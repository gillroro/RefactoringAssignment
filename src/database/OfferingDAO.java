package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import entity.Course;
import entity.Offering;

public class OfferingDAO {

	private Offering offering;
	private static Connection conn;
	private static Statement statement;

	public static Offering create(Course course, String daysTimesCsv) throws Exception {

		try {
			conn = ConnectionCreation.getConnection();
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + course.getName() + "','" + daysTimesCsv + "');");
			DBUtil.close(result);
			return new Offering(newId, course, daysTimesCsv);
		} 
		finally {
			try { 
				DBUtil.close(statement);
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
	}

	public static Offering find(int id) {
		try {
			conn = ConnectionCreation.getConnection();
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE id =" + id + ";");
			if (result.next() == false)
				return null;
			String courseName = result.getString("name");
			Course course = CourseDAO.find(courseName);
			String dateTime = result.getString("daysTimes");
			
			DBUtil.close(result);
			return new Offering(id, course, dateTime);
		} 
		catch (Exception ex) {
			try { 
				DBUtil.close(statement);
				DBUtil.close(conn);
			} catch (Exception ignored) {}
			return null;
		}
	}

	public void update() throws Exception {
		try {
			conn = ConnectionCreation.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + offering.getId() + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + offering.getId() + "','" + offering.getCourse().getName() + "','" + offering.getDaysTimes() + "');");
		} 
		finally {
			try { 
				DBUtil.close(statement);
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
	}


}
