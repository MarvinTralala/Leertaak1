package server;

import java.net.UnknownHostException;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Collection;



import java.util.LinkedList;

import com.mongodb.*;

public class Database implements Runnable{
	
	private MongoClient mongoClient;
	private DB db;
	private DBCollection collection;
	
	private LinkedList<BasicDBObject> queue;
	
	public Database() {
		//when instantiating this class, immediately create a connection with the database.
		try {
			mongoClient = new MongoClient("localhost");		//connection with server
			db = mongoClient.getDB("UNWDMI");				//create Database with name "UNWDMI"
			collection = db.getCollection("measurements");	//create collections to insert into database
			System.out.println("mongo database created");
		} catch (UnknownHostException e) { e.printStackTrace();	}
		
		System.out.println("table Measurement created");
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
	 * Checks in the queue for any objects to put in collection
	 * Always stays in the while loop with the try-catch!!
	 */
	public void run(){
		while(true){
			try {
				System.out.println("DB HANDLE -> " + collection.getCount()); //get count of measurements
				while(!queue.isEmpty()) {
					BasicDBObject object = queue.removeFirst();
					collection.insert(object);
				}
			} catch(Exception e) {
				System.out.println("---------------------------------------------------");
			}
		}
	}
	
	/*
	public void executeAndPrintResultsOfSelectStatement(String query) {
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = 0;
			int columnsNumber = rsmd.getColumnCount();
		    while (rs.next()) {
		        for (int i = 1; i <= columnsNumber; i++) {
		            if (i > 1) System.out.print(",  ");
		            String columnValue = rs.getString(i);
		            System.out.print(columnValue + " " + rsmd.getColumnName(i));
		        }
		        count++;
		        System.out.println("");
		    }
		    System.out.println("Total number of rows: " + count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}  */
	
	public void disconnect() {
		//make sure the connection can be closed again
		System.out.println("Database connection closed");
		//conn.close();
	}  

}
