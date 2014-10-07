import com.sun.corba.se.spi.activation.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Marvin on 7-10-2014.
 */
public class Main {

    public static void main (String args[]) {

        //serversocket. create client threads..
        try {
            ServerSocket ss = new ServerSocket(7789);

            int i = 0;
            while (true) {
                new ClientThread(ss.accept(), i).start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        /*try {


            ServerSocket ss = new ServerSocket(7789);
            Socket client = ss.accept();

            System.out.println("Client connected");

            BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while (true) {
                String s = bf.readLine();
                System.out.println(s);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
