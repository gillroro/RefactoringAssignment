package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import entity.Offering;
import entity.Schedule;

public class ScheduleDAO{
	
	private static Connection conn;
	private Schedule schedule;
	private static Statement statement;
	
	public static void deleteAll() throws Exception {
		
		try {
			conn = ConnectionCreation.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule");
		} 
		finally {
			try { 
				DBUtil.close(statement);
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
	}

	public static Schedule create(String name) throws Exception {
		
		try {
			conn = ConnectionCreation.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			return new Schedule(name);
		} 
		finally {
			try { 
				DBUtil.close(statement);//closes off the connection to the Statement
				DBUtil.close(conn);//closes the connection to the database
			} 
			catch (Exception ignored) {}
		}
	}

	public static Schedule find(String name) {
		
		try {
			conn = ConnectionCreation.getConnection();
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE name= '" + name + "';");
			Schedule schedule = new Schedule(name);
			while (result.next()) {
				int offeringId = result.getInt("offeringId");
				Offering offering = OfferingDAO.find(offeringId);
				schedule.add(offering);
			}
			
			DBUtil.close(result);
			return schedule;
		} 
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		} 
		finally {
			try { 
				DBUtil.close(statement);
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
	}

	public static Collection<Schedule> all() throws Exception {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		
		try {
			conn = ConnectionCreation.getConnection();
			statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT Name FROM schedule;");
			while (results.next())
				result.add(ScheduleDAO.find(results.getString("Name")));
			
			DBUtil.close(results);
		} 
		finally {
			try { 
				DBUtil.close(statement);
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}
		return result;
	}

	public void update() throws Exception {
	
		try {
			conn = ConnectionCreation.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + schedule.getName() + "';");
			for (int i = 0; i < schedule.getOfferings().size(); i++) {
				Offering offering = (Offering) schedule.getOfferings().get(i);
				statement.executeUpdate("INSERT INTO schedule(name, offeringId) VALUES('" + schedule.getName() + "','" + offering.getId() + "');");
			}
			
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
