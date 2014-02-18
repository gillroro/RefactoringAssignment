package entity;
import java.util.*;
import java.sql.*;

import database.ConnectionCreation;

public class Schedule {

	String name;
	int credits = 0;
	static final int minCredits = 12;
	static final int maxCredits = 18;
	boolean permission = false;
	
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

	
	
	
}
