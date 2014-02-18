package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionCreation {
	
	static String url = "jdbc:mysql://localhost:3306/registration";
	Connection connection = null;

	public Connection getConnection(){
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
			connection = DriverManager.getConnection(url, "root", "root");
			
			
		}
		catch (Exception ignored) {} 
		return connection;
	}

}
