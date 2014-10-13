package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
	
	/*
	 * TODO BEFORE RUNNING!
	 * 1. import library mysql-connector/j (jar file found on mysql.org)
	 * 2. import unwdmi.sql to database
	 * 3. create user using the following statements in mysql
	 * 		CREATE USER 'unwdmi'@'localhost' IDENTIFIED BY 'unwdmi';
	 * 		GRANT ALL PRIVILEGES ON unwdmi.* TO 'unwdmi'@'localhost';
	 * 		FLUSH PRIVILEGES;
	 * 4. run app! :)
	 */
	
	public static boolean ServerStatus = true;
	
	public static void main(String[] args) {
		try {
			//create a new server listening to port x
			ServerSocket s = new ServerSocket(7789);
			int i = 0;
			
			while (ServerStatus) {
				//accept new connection on a new thread
				new Thread(new Worker(s.accept(), i)).start();
				i++;
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
