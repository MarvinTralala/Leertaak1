package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Worker extends Thread {
	
	Socket client;
	BlockingQueue<String> que;
	
	public Worker(Socket client, int id) {
		this.client = client;		
		this.que = new ArrayBlockingQueue<String>(100);
		
		// note: handling the documentparsing on a new thread allows this thread to continue listening to new messages
		new Thread(new DOMParser(que)).start();
		
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
					// passing the StringBuilder as a single String to the queue.
            		// using a queue allows the program to buffer information to send to the database
            		que.add(sb.toString());
					
					// Clear the contents of the StringBuilder to start over properly.
					sb.delete(0, sb.length());
				}
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
