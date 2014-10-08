import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;
import java.sql.*;

/**
 * Created by Marvin on 7-10-2014.
 */
public class Database {
    //maakt connectie met de database
	private static Connection conn = null;
	private static String username = "", password = "";
	private static int idStation;
	private static int temperatuur, dauwpunt, luchtdruk, zichtbaarheid;
	private static int neerslag, sneeuwdiepte, bewolking, windrichting, windsnelheid, gebeurtenissen;
	SimpleDateFormat date = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	// driver door db
	private static driver = "com.mysql.jdbc.Driver";
	
	// method om connectie te maken met de db
	public static boolean connect() {
		boolean isConnect = false;
		try {
			Class.forName(driver);
			// get connection to the db specified by connectionUrl
			conn = (Connection) DriverManager.getConnection("connectionURL" + username + password);
			isConnect = true;
		} catch (SQLException sql) {
			system.out.println("SQL exception thrown: " + sql);
		}
			return isConnect;
	}

    //heeft functies om data op de juiste manier te inserten
    public boolean insertMeasurement(String[] args) {
        //open
    	connect();
    try {
    	// create a statement
    	Statement stmt = conn.createStatement();
    	//insert
    	stmt.executeUpdate("Insert query");
    } catch (Exception e) {
    	system.out.println(e);
    } finally {
        //close connection
        disConnect();
    } 
        return true;
   }
       
    // method for closing db
    public static void disConnect() {
    	try {
    		if(conn == null) {
    			conn.close();
    			conn = null;
    			}
    	} catch (exception e) {
    		system.out.println(e);
    	}
    }
}
