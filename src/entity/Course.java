package entity;

public class Course {
	//Changed this class into a POJO. 
	//It should only know about the instance variables and have a getter and setter method for each variable.
	//Took out all the database activity into a new class.
	
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
