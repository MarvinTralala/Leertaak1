package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Commander {
	
	ServerSocket server;
	boolean running;
	Database database;
	
	public Commander() {
		System.out.println("Welcome to the app made by Marvin and Hofsep");
		System.out.println("Type 'help' for a list of commands");
		
		database = new Database();
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
				database.executeQuery();
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
		new Thread(database).start();  //start the database thread to put object into DB
		try {
			server = new ServerSocket(7789);
			int i = 0;
			while(true) {
					new Thread(new Worker(server.accept(), i, database)).start();
					i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
