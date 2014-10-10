package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
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
