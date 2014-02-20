package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import entity.Course;

public class CourseCreation {

	private Course course; 
	private static Connection conn;

	public static Course create(String name, int credits) throws Exception {
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course (name, credits) VALUES ('" + name + "', '" + credits + "');");
			DBUtil.close(statement);
			return new Course(name, credits);
		} 
		finally {
			try { 
				DBUtil.close(conn);
				
			} 
			catch (Exception ignored) {}
		}
	}

	public static Course find(String name) {
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE Name = '" + name + "';");
			if (!result.next()) return null;
			int credits = result.getInt("Credits");
			DBUtil.close(statement);
			DBUtil.close(result);
			return new Course(name, credits);
		} 
		catch (Exception ex) {
			return null;
		} 
		finally {
			try { 
				DBUtil.close(conn); 
			} 
			catch (Exception ignored) {}
		}
	}

	public void update() throws Exception {
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + course.getName() + "';");
			statement.executeUpdate("INSERT INTO course (name, credits) VALUES('" + course.getName() + "','" + course.getCredits() + "');");
			DBUtil.close(statement);
			
		} 
		finally {
			try { 
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
	}

}
