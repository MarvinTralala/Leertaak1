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
	Handler handler;
	int id;
	
	public Worker(Socket client, int id) {
		this.id = id;
		this.client = client;		
		this.que = new ArrayBlockingQueue<String>(100);
		
		handler = new Handler(que);
		
		//note: handling the document parsing on a new thread allows this thread to continue listening to new messages
		new Thread(new Handler(que)).start();
		
		//make a notice of the thread starting
		System.out.println("Worker " + id + " started.");
	}
	
	@Override
	public void run() {
		try {
		  	BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
		  	//before creating the document, we must separate the incoming data in separate XML 'files'
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
            //ex.printStackTrace();
        	System.out.println(id + " >> System can't handle the load! shutting down...");
        	handler.stopExecuting();
        } catch (NullPointerException e) {
        	System.out.println(id + " >> Feed stopped! shutting down thread...");
        	handler.stopExecuting();
        }
	}
}
