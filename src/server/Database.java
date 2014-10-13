package server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class Database {
	
	private final String DATABASE = "unwdmi";
	private final String USERNAME = "unwdmi";
	private final String PASSWORD = "unwdmi";
	
	private Connection conn;
	
	public Database() {
		//when instantiating this class, immediately create a connection with the database.
		this.conn = connect();
	}
	
	private Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			//try to make a connection with the database using config-data on top of this page
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/" + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		return null;
	}
	
	public void insertMeasurement(int stn, String date, String time, double temp, double dewp, double stp, double slp, double visib, double wdsp, double prcp, double sndp, String frshtt, double cldc, int wnddir) {
		try {
			//insert formatted data into the database
			Statement s = conn.createStatement();
			s.executeUpdate("INSERT INTO measurements (stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir) "
					+ "VALUES (" + stn + ", '" + date + "', '" + time + "', " + temp + ", " + dewp + ", " + stp + ", " + slp + ", " + visib + ", " + wdsp + ", " + prcp + ", " + sndp + ", '" + frshtt + "', " + cldc + ", " + wnddir + ")");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("values " + stn + " " + date + " " + time + " " + temp + " " + dewp + " " + stp + " " + slp + " " + visib + " " + wdsp + " " + prcp + " " + sndp + " " + frshtt + " " + cldc + " " + wnddir);
		}
		
	}
	
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
	}
	
	public void disconnect() {
		//make sure the connection can be closed again
		System.out.println("Database connection closed");
		//conn.close();
	}

}
