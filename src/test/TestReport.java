package test;
import static org.junit.Assert.*;

import org.junit.Test;

import database.CourseCreation;
import database.OfferingCreation;
import database.ScheduleCreation;
import entity.Course;
import entity.Offering;
import entity.Report;
import entity.Schedule;

public class TestReport  {

	public TestReport() { 
		
	}

	@Test
	public void testEmptyReport() throws Exception {
		ScheduleCreation.deleteAll();
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		assertEquals("Number of scheduled offerings: 0\n", buffer.toString());
	}
	
	@Test
	public void testReport() throws Exception {
		ScheduleCreation.deleteAll();
		Course cs101 = CourseCreation.create("CS101", 3);
		//cs101.update();
		Offering off1 = OfferingCreation.create(cs101, "M10");
		off1.update();
		Offering off2 = OfferingCreation.create(cs101, "T9");
		off2.update();
		Schedule s = ScheduleCreation.create("Bob");
		s.add(off1);
		s.add(off2);
		s.update();
		Schedule s2 = ScheduleCreation.create("Alice");
		s2.add(off1);
		s2.update();
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		String result = buffer.toString();
		String valid1 = "CS101 M10\n\tAlice\n\tBob\n" + "CS101 T9\n\tBob\n" + "Number of scheduled offerings: 2\n";
		String valid2 = "CS101 T9\n\tBob\n" + "CS101 M10\n\tBob\n\tAlice\n" + "Number of scheduled offerings: 2\n";
		assertTrue(result.equals(valid1) || result.equals(valid2));
	}
}
