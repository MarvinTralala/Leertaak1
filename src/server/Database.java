package server;

import java.sql.*;

import com.mysql.jdbc.Connection;

public class Database {
	
	private final String DATABASE = "unwdmi";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	
	private Connection conn;
	
	public Database() {
		this.conn = connect();
	}
	
	private Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/" + DATABASE + "?user=" + USERNAME + "&password=" + PASSWORD);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void insertMeasurement(int stn, String date, String time, double temp, double dewp, double stp, double slp, double visib, double wdsp, double prcp, double sndp, String frshtt, double cldc, int wnddir) {
		try {
			Statement s = conn.createStatement();
			s.executeUpdate("INSERT INTO measurements (stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir) "
					+ "VALUES (" + stn + ", '" + date + "', '" + time + "', " + temp + ", " + dewp + ", " + stp + ", " + slp + ", " + visib + ", " + wdsp + ", " + prcp + ", " + sndp + ", '" + frshtt + "', " + cldc + ", " + wnddir + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
