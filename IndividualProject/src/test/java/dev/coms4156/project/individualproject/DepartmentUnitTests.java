package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * This is a test class for all the methods in the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  private Map<String, Course> courses;

  /**
   * Run before every test. Sets up the department static instance to be tested.
   */
  @BeforeEach
  public void setupCourseForTesting() {
    courses = new HashMap<>();
    courses.put("1000", new Course("Ins1", "Loc1", "Time1", 300));
    courses.put("1001", new Course("Ins2", "Loc2", "Time2", 200));
    courses.put("1002", new Course("Ins3", "Loc3", "Time3", 250));
    testDepartment = new Department("COMS", courses, "Test Chair", 250);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "COMS 1002: \n" + "Instructor: Ins3; Location: Loc3; Time: Time3\n"
        + "COMS 1001: \n" + "Instructor: Ins2; Location: Loc2; Time: Time2\n" + "COMS 1000: \n"
        + "Instructor: Ins1; Location: Loc1; Time: Time1\n";
    assertEquals(expectedResult, testDepartment.toString());
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(250, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Test Chair", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    assertEquals(courses, testDepartment.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(251, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(249, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest_failure() {
    testDepartment = new Department("COMS", courses, "Test Chair", 0);
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    testDepartment.addCourse("1004", new Course("New Instructor", "New Location", "New Time Slot",
        10));
    assertEquals("New Instructor", testDepartment.getCourseSelection()
        .get("1004").getInstructorName());
  }

  @Test
  public void createCourseTest() {
    testDepartment.createCourse("1004", "New Instructor", "New Location", "New Time Slot", 10);
    assertEquals("New Location",
        testDepartment.getCourseSelection().get("1004").getCourseLocation());
  }

  /** The test department instance used for testing. */
  public static Department testDepartment;
}