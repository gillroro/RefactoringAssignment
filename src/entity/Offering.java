package entity;
import java.sql.Connection;
import java.sql.Statement;

import database.ConnectionCreation;

//Changed this class into a POJO. 
//It should only know about the instance variables and have a getter and setter method for each variable.
//Took out all the database activity into a new class.

public class Offering {
	private int id;
	private Course course;
	private String daysTimes;

	private static Connection conn;

	public Offering(int id, Course course, String daysTimesCsv) {
		this.id = id;
		this.course = course;
		this.daysTimes = daysTimesCsv;
	}

	public int getId() {
		return id;
	}

	public Course getCourse() {
		return course;
	}

	public String getDaysTimes() {
		return daysTimes;
	}

	public String toString() {
		return "Offering " + getId() + ": " + getCourse() + " meeting " + getDaysTimes();
	}



	public void update() throws Exception {
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + id + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + id + "','" + course.getName() + "','" + daysTimes + "');");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
}