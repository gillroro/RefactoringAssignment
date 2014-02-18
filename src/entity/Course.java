package entity;
import java.sql.*;

import database.ConnectionCreation;

public class Course {
	private String name;
	private int credits;

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
