package database;

public class ConnectionCreation {
	
	static String url = "jdbc:mysql://localhost:3306/registration";
	static { 
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
		}
		catch (Exception ignored) {} 
	}

}
