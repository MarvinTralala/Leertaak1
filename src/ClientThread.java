import javax.xml.parsers.SAXParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Marvin on 7-10-2014.
 */
public class ClientThread extends Thread {

    Socket client;
    Database db;

    public ClientThread(Socket client, int id) {
        this.client = client;
        this.db = new Database();

        System.out.println("Thread " + id + ": Yes,  i'm started!");
    }

    @Override
    public void run() {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            DOMParser dp = new DOMParser(client.getInputStream());

            while (true) {
                //System.out.println(bf.readLine());


                /**
                //System.out.println(bf.readLine());
                // TODO: sax implement
                // TODO: datacheck (niet instantieren)
                // TODO: to database

                //String[] s = {"","",""};
                //db.insertMeasurement(s);*/
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
