package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Handler extends Thread {
	
	boolean running;
	BlockingQueue<String> que;
	History history;
	Corrector corrector;
	Database database;
	
	public Handler(BlockingQueue<String> que, Database dt) {
		this.que = que;
		running = true;
		history = new History();
		corrector = new Corrector(history);
		database = dt;
	}
	
	@Override
	public void run() {
		while(running) {
			try{
				//Queue acts as a buffer for when the data is not handled quick enough
				String xml = que.take();
				
				//Built XML document
				DocumentBuilderFactory databaseThreadFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder databaseThreaduilder = databaseThreadFactory.newDocumentBuilder();
				Document doc = databaseThreaduilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
				doc.getDocumentElement().normalize();
				
				//get all nodes
				NodeList nList = doc.getElementsByTagName("MEASUREMENT");
				
				// Iterate through the nodes of the provided XML. Thusly instantiating
				// a new Measurement object for every node. Let it be clear that the
				// tag "<MEASUREMENT>" signifies a node.
				for(int i = 0; i < nList.getLength(); i++){
					// Representation of the current node.
					Node node = nList.item(i);
					
					// Check if the node is an element node.
					if(node.getNodeType() == Node.ELEMENT_NODE){
						Element element = (Element)node;
						
						// Instantiate variables for all the data in the measurement.
						int stn, wnddir;
						String date = null, time = null, frshtt;
						double temp, dewp, stp, slp, visib, wdsp, prcp, sndp, cldc;
						
						// Set default values for all the data, in case any data is missing.
						temp = dewp = stp = slp = visib = wdsp = prcp = sndp = cldc = 0.0d;
						stn = 000000;
						frshtt = "000000";
						wnddir = 0;
											
						// Check every element of the measurement to make sure it isn't empty.
						// If not empty, set the variable value equal to the element value,
						// parsing it to the correct data type in the process.
						if(!(element.getElementsByTagName("STN").item(0).getTextContent().isEmpty())){
							stn = Integer.parseInt(element.getElementsByTagName("STN").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("DATE").item(0).getTextContent().isEmpty())){
							date = element.getElementsByTagName("DATE").item(0).getTextContent();
						}
						if(!(element.getElementsByTagName("TIME").item(0).getTextContent().isEmpty())){
							time = element.getElementsByTagName("TIME").item(0).getTextContent();
						}
						if(!(element.getElementsByTagName("TEMP").item(0).getTextContent().isEmpty())){
							temp = Double.parseDouble(element.getElementsByTagName("TEMP").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("DEWP").item(0).getTextContent().isEmpty())){
							dewp = Double.parseDouble(element.getElementsByTagName("DEWP").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("STP").item(0).getTextContent().isEmpty())){
							stp = Double.parseDouble(element.getElementsByTagName("STP").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("SLP").item(0).getTextContent().isEmpty())){
							slp = Double.parseDouble(element.getElementsByTagName("SLP").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("VISIB").item(0).getTextContent().isEmpty())){
							visib = Double.parseDouble(element.getElementsByTagName("VISIB").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("WDSP").item(0).getTextContent().isEmpty())){
							wdsp = Double.parseDouble(element.getElementsByTagName("WDSP").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("PRCP").item(0).getTextContent().isEmpty())){
							prcp = Double.parseDouble(element.getElementsByTagName("PRCP").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("SNDP").item(0).getTextContent().isEmpty())){
							sndp = Double.parseDouble(element.getElementsByTagName("SNDP").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("FRSHTT").item(0).getTextContent().isEmpty())){
							frshtt = element.getElementsByTagName("FRSHTT").item(0).getTextContent();
						}
						if(!(element.getElementsByTagName("CLDC").item(0).getTextContent().isEmpty())){
							cldc = Double.parseDouble(element.getElementsByTagName("CLDC").item(0).getTextContent());
						}
						if(!(element.getElementsByTagName("WNDDIR").item(0).getTextContent().isEmpty())){
							wnddir = Integer.parseInt(element.getElementsByTagName("WNDDIR").item(0).getTextContent());
						}
						
						//create measurement object
						Measurement m = new Measurement(stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir);
						
						//correct the measurement
						Measurement corrected = corrector.correctMeasurement(m);
						
						//get the corrected values
						stn = corrected.getStn();
						date = corrected.getDate();
						time = corrected.getTime();
						temp = corrected.getTemp();
						dewp = corrected.getDewp();
						stp = corrected.getStp();
						slp = corrected.getSlp();
						visib = corrected.getVisib();
						wdsp = corrected.getWdsp();
						prcp = corrected.getPrcp();
						sndp = corrected.getSndp();
						frshtt = corrected.getFrshtt();
						cldc = corrected.getCldc();
						wnddir = corrected.getWnddir();
						
						//add the corrected measurement to history
						history.addMeasurement(corrected);
						
						//add new measurement values to the database queue
						database.insertMeasurementGetObject(stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir);
					}
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopExecuting() {
		//when the feeding thread stops.
		database.disconnect();
		running = false;
	}

}
