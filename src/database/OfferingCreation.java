package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import entity.Course;
import entity.Offering;

public class OfferingCreation {
	
	private Offering offering;
	private static ConnectionCreation createConnection = new ConnectionCreation(); 
	private static Connection conn;

	public static Offering create(Course course, String daysTimesCsv) throws Exception {
		
		try {
			conn = createConnection.getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + course.getName() + "','" + daysTimesCsv + "');");
			return new Offering(newId, course, daysTimesCsv);
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Offering find(int id) {
		try {
			conn = createConnection.getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE ID =" + id + ";");
			if (result.next() == false)
				return null;
			String courseName = result.getString("Course");
			Course course = CourseCreation.find(courseName);
			String dateTime = result.getString("DateTime");
			conn.close();
			return new Offering(id, course, dateTime);
		} 
		catch (Exception ex) {
			try { 
				conn.close(); 
			} catch (Exception ignored) {}
			return null;
		}
	}

	public void update() throws Exception {
		try {
			conn = createConnection.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + offering.getId() + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + offering.getId() + "','" + offering.getCourse().getName() + "','" + offering.getDaysTimes() + "');");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}


}