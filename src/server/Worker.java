package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Worker extends Thread {
	
	Socket client;
	Database db;
	
	public Worker(Socket client, int id) {
		this.client = client;
		this.db = new Database();
		
		//make a notice of the thread starting
		System.out.println("Worker " + id + " started.");
	}
	
	@Override
	public void run() {
		try {
		  	BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
		  	
		  	//before creating de document, we must seperate the incoming data in seperate xml 'files'
		  	StringBuilder sb = new StringBuilder();
		  	String stream;
		  	
            while (!(stream = bf.readLine()).equals(null)) {
            	sb.append(stream);
                
            	if(stream.equals("</WEATHERDATA>")){
					// If true, instantiate a new DOMParser to process the cluster by means of
					// passing the StringBuilder as a single String with the Handler.
            		// note: handling the documentparsing on a new thread allows this thread to continue listening to new messages
					new Thread(new DOMParser(sb.toString())).start();
					
					// Clear the contents of the StringBuilder to start over properly.
					sb.delete(0, sb.length());
				}
            	
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
