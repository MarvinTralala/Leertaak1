package server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import com.mongodb.*;

public class Database implements Runnable{
	
	private MongoClient mongoClient;
	private DB db;
	private DBCollection measurements;
	private DBCollection stations;
	
	private LinkedList<BasicDBObject> queue;
	
	public Database() {
		//when instantiating this class, immediately create a connection with the database.
		try {
			mongoClient = new MongoClient("localhost");		//connection with server
			db = mongoClient.getDB("UNWDMI");				//create Database with name "UNWDMI"
			measurements = db.getCollection("measurements");	//create collection to insert into database
			stations = db.getCollection("stations");
			
			System.out.println("mongo database created");
		} catch (UnknownHostException e) { e.printStackTrace();	}
		
		//start a list for all object (measurements in the object) to put in database
		queue = new LinkedList<BasicDBObject>();
	}
	
	/**
	 * Get the corrected measurements and make an object to place in the queue
	 */
	public synchronized void insertMeasurementGetObject(int stn, String date, String time, double temp, double dewp, double stp, double slp, double visib, double wdsp, double prcp, double sndp, String frshtt, double cldc, int wnddir) {
		
		BasicDBObject doc = new BasicDBObject("stn",stn)
				.append("date", date)
				.append("time", time)
				.append("temp", temp)
				.append("dewp", dewp)
				.append("stp", stp)
				.append("slp", slp)
				.append("visib", visib)
				.append("wdsp", wdsp)
				.append("prcp", prcp)
				.append("sndp", sndp)
				.append("frshtt", frshtt)
				.append("cldc", cldc)
				.append("wnddir", wnddir);
		
		queue.addLast(doc);  //adds the object in list for queue
	}
	
	/**
	 * Create an infinite loop thread
	 * Checks in the queue for any objects to put in measurements
	 * Always stays in the while loop with the try-catch!!
	 */
	public void run(){
		while(true){
			synchronized(this){
				try {
					while(!queue.isEmpty()) {
						BasicDBObject object = queue.removeFirst();
						measurements.insert(object);
						System.out.println("Handled: " + measurements.getCount());
					}
				} catch(Exception e) {
					System.out.println("---------------------------------------------------");
				}
			}
		}
	}
	
	/**
	 * Execute query.
	 * All the stations in Norway for a period of time
	 */
	public void executeQuery(){
		
		ArrayList<Integer> stationList = new ArrayList<Integer>();
		DBCursor cursor = stations.find(new BasicDBObject("country", "KENYA"));
		
		while(cursor.hasNext()){
			BasicDBObject station = (BasicDBObject)cursor.next();
			stationList.add(station.getInt("stn"));
			//System.out.println(cursor.next());
		}
		
		System.out.println("Amount of stations = " + stationList.size());
		
		BasicDBObject timezone = new BasicDBObject("$lt", "2014-10-25")
										.append("$gte", "2014-10-24");
		BasicDBObject query = new BasicDBObject("date", timezone)
										.append("stn", new BasicDBObject("$in", stationList));
		DBCursor cursor2 = measurements.find(query);

		while(cursor2.hasNext()){
			System.out.println(cursor2.next());
		}
		System.out.println("Total measurements = " + cursor2.count());
	} 
	
	public void disconnect() {
		//make sure the connection can be closed again
		System.out.println("Database connection closed");
		//conn.close();
	}  

}
