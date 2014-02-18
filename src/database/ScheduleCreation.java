package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import entity.Course;
import entity.Offering;
import entity.Schedule;

public class ScheduleCreation {
	
	private static Connection conn;
	private static ConnectionCreation createConnection = new ConnectionCreation(); 
	private Schedule schedule;
	
	public static void deleteAll() throws Exception {
		
		try {
			conn = createConnection.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Schedule create(String name) throws Exception {
		
		try {
			conn = createConnection.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			return new Schedule(name);
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Schedule find(String name) {
		
		try {
			conn = createConnection.getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE Name= '" + name + "';");
			Schedule schedule = new Schedule(name);
			while (result.next()) {
				int offeringId = result.getInt("OfferingId");
				Offering offering = Offering.find(offeringId);
				schedule.add(offering);
			}
			return schedule;
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

	public static Collection<Schedule> all() throws Exception {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		
		try {
			conn = createConnection.getConnection();
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT Name FROM schedule;");
			while (results.next())
				result.add(ScheduleCreation.find(results.getString("Name")));
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
		return result;
	}

	public void update() throws Exception {
	
		try {
			conn = createConnection.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + schedule.getName() + "';");
			for (int i = 0; i < schedule.getOfferings().size(); i++) {
				Offering offering = (Offering) schedule.getOfferings().get(i);
				statement.executeUpdate("INSERT INTO schedule(name, offeringId) VALUES('" + schedule.getName() + "','" + offering.getId() + "');");
			}
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}


}
