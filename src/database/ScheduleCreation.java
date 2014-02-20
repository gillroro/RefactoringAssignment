package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import entity.Offering;
import entity.Schedule;

public class ScheduleCreation {
	
	private static Connection conn;
	private Schedule schedule;
	
	public static void deleteAll() throws Exception {
		
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule");
			DBUtil.close(statement);
		} 
		finally {
			try { 
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
	}

	public static Schedule create(String name) throws Exception {
		
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			DBUtil.close(statement);
			return new Schedule(name);
		} 
		finally {
			try { 
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
	}

	public static Schedule find(String name) {
		
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE name= '" + name + "';");
			Schedule schedule = new Schedule(name);
			while (result.next()) {
				int offeringId = result.getInt("offeringId");
				Offering offering = OfferingCreation.find(offeringId);
				schedule.add(offering);
			}
			DBUtil.close(statement);
			DBUtil.close(result);
			return schedule;
		} 
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		} 
		finally {
			try { 
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
	}

	public static Collection<Schedule> all() throws Exception {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT Name FROM schedule;");
			while (results.next())
				result.add(ScheduleCreation.find(results.getString("Name")));
			DBUtil.close(statement);
			DBUtil.close(results);
		} 
		finally {
			try { 
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
		return result;
	}

	public void update() throws Exception {
	
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + schedule.getName() + "';");
			for (int i = 0; i < schedule.getOfferings().size(); i++) {
				Offering offering = (Offering) schedule.getOfferings().get(i);
				statement.executeUpdate("INSERT INTO schedule(name, offeringId) VALUES('" + schedule.getName() + "','" + offering.getId() + "');");
			}
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
