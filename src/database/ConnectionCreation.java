package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreation {
	
	//A class that allows for the connection to the database. 
	//Uses a singleton pattern- to create one object of this class and many Connection objects 
	
	private static ConnectionCreation instance = new ConnectionCreation();
	private static final String url = "jdbc:mysql://localhost:3306/registration";
	private static final String driver= "com.mysql.jdbc.Driver";
	static Connection connection = null;
	
	private ConnectionCreation(){
		try { 
			Class.forName(driver); 
		}
		catch (Exception ignored) {} 
	}

	public Connection createConnection(){
		try { 
			
			connection = DriverManager.getConnection(url, "root", "root");
			
			
		}
		catch (Exception ignored) {} 
		return connection;
	}
	
	public static Connection getConnection(){
		return instance.createConnection();
	}
	
	public static void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
