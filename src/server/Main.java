package server;


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
		Commander command = new Commander();
		command.read();	
	}
}
