package entity;
import java.sql.*;

public class Course {
	private String name;
	private int credits;
	static String url = "jdbc:mysql://localhost:3306/registration";
	static { 
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
		}
		catch (Exception ignored) {} 
	}

	public static Course create(String name, int credits) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "root");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course (name, credits) VALUES ('" + name + "', '" + credits + "');");
			return new Course(name, credits);
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Course find(String name) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "root");
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE Name = '" + name + "';");
			if (!result.next()) return null;
			int credits = result.getInt("Credits");
			return new Course(name, credits);
		} 
		catch (Exception ex) {
			return null;
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public void update() throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "root");
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course (name, credits) VALUES('" + name + "','" + credits + "');");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public Course(String name, int credits) {
		this.name = name;
		this.credits = credits;
	}

	public int getCredits() {
		return credits;
	}

	public String getName() {
		return name;
	}
}