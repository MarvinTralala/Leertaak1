package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Commander {
	
	ServerSocket server;
	boolean running;
	
	public Commander() {
		System.out.println("Welcome to the app made by Marvin, Hofsep and Bakr");
		System.out.println("Type 'help' for a list of commands");
		running = true;
	}
	
	public void read() {
		Scanner scanner = new Scanner(System.in);
		while(running) {
			String cmd = scanner.nextLine();
			
			switch (cmd) {
			case "help":
				System.out.println("Type 'sql' to get the query from the assignment");
				System.out.println("Type 'server' to start the server.");
				break;
			case "sql":
	//			executeQuery();
				break;
			case "server":
				startServer();
				break;
			default:
				System.out.println("That is not a valid command. Type 'help' to get a list of available commands.");
				break;
			}
		}
		scanner.close();
	}
	
	/**
	 * Start listening to the server for connection and place connections in a thread
	 * While loop is infinite with the try-catch block
	 */
	private void startServer() {
		System.out.println("Server started...");
		Database database = new Database();
		new Thread(database).start();  //start the database thread to put object into DB
		try {
			server = new ServerSocket(7789);
			int i = 0;
			while(true) {
				try {
					new Thread(new Worker(server.accept(), i, database)).start();
					i++;
				} catch(Exception e) {
					//for every new thread made while no connection is available
					System.out.println("no connection available for new thread!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	private void executeQuery() {
		Database db = new Database();
		StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("m.stn, date, time, country ");
		query.append("FROM ");
		query.append("`measurements` m ");
		query.append("INNER JOIN `stations` s ON s.stn = m.stn ");
		query.append("WHERE ");
		query.append("date >= DATE_SUB(NOW(), INTERVAL 2 DAY) ");
		query.append("AND time >= NOW() ");
		query.append("AND s.country = 'NORWAY' ");
		
		db.executeAndPrintResultsOfSelectStatement(query.toString());
		db.disconnect();
		
		System.out.println("Query: " + query.toString());
	}  */
}
