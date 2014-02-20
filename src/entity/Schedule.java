package entity;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import database.ConnectionCreation;
import database.DBUtil;

public class Schedule {
	//Changed this class into a POJO. 
	//It should only know about the instance variables and have a getter and setter method for each variable.
	//Took out all the database activity into a new class.

	String name;
	int credits = 0;
	static final int minCredits = 12;
	static final int maxCredits = 18;
	boolean permission = false;
	private static Connection conn;
	
	public ArrayList<Offering> offerings = new ArrayList<Offering>();


	public Schedule(String name) {
		this.name = name;
	}


	public String toString() {
		return "Schedule " + name + ": " + offerings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Offering> getOfferings() {
		return offerings;
	}

	public void setOfferings(ArrayList<Offering> offerings) {
		this.offerings = offerings;
	}
	
	public void add(Offering offering) {
	
		credits += offering.getCourse().getCredits();
		offerings.add(offering);
	}
	
	public void authorizeOverload(boolean authorized) {
		permission = authorized;
	}

	public List<String> analysis() {
		ArrayList<String> result = new ArrayList<String>();
		if (credits < minCredits)
			result.add("Too few credits");
		if (credits > maxCredits && !permission)
			result.add("Too many credits");
		checkDuplicateCourses(result);
		checkOverlap(result);
		return result;
	}

	public void checkDuplicateCourses(ArrayList<String> analysis) {
		HashSet<Course> courses = new HashSet<Course>();
		for (int i = 0; i < offerings.size(); i++) {
			Course course = ((Offering) offerings.get(i)).getCourse();
			if (courses.contains(course))
				analysis.add("Same course twice - " + course.getName());
			courses.add(course);
		}
	}

	public void checkOverlap(ArrayList<String> analysis) {
		HashSet<String> times = new HashSet<String>();
		for (Iterator<Offering> iterator = offerings.iterator(); iterator.hasNext();) {
			Offering offering = (Offering) iterator.next();
			String daysTimes = offering.getDaysTimes();
			StringTokenizer tokens = new StringTokenizer(daysTimes, ",");
			while (tokens.hasMoreTokens()) {
				String dayTime = tokens.nextToken();
				if (times.contains(dayTime))
					analysis.add("Course overlap - " + dayTime);
				times.add(dayTime);
			}
		}
	}
	
	public void update() throws Exception {
		
		try {
			conn = ConnectionCreation.getConnection();
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			for (int i = 0; i < offerings.size(); i++) {
				Offering offering = (Offering) offerings.get(i);
				statement.executeUpdate("INSERT INTO schedule(name, offeringId) VALUES('" + name + "','" + offering.getId() + "');");
			}
		} 
		finally {
			try { 
				DBUtil.close(conn);
			} 
			catch (Exception ignored) {}
		}

	
	}
	
}
