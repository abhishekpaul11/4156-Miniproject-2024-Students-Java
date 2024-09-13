package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is a test class for all the methods in the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentTest_Failure() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void enrollStudentTest_Success() {
    testCourse.setEnrolledStudentCount(240);
    assertTrue(testCourse.enrollStudent());
  }

  @Test
  public void dropStudentTest_Success() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }

  @Test
  public void dropStudentTest_Failure() {
    testCourse.setEnrolledStudentCount(240);
    assertTrue(testCourse.dropStudent());
  }

  @Test
  public void getCourseLocationTest() {
    assertEquals("417 IAB", testCourse.getCourseLocation());
  }

  @Test
  public void getCourseInstructorNameTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("testLocation");
    assertEquals("testLocation", testCourse.getCourseLocation());
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("testInstructorName");
    assertEquals("testInstructorName", testCourse.getInstructorName());
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("testTimeSlot");
    assertEquals("testTimeSlot", testCourse.getCourseTimeSlot());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(240);
    assertEquals(testCourse.getEnrolledStudentCount(), 240);
  }

  @Test
  public void getEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(250);
    assertEquals(testCourse.getEnrolledStudentCount(), 250);
  }

  @Test
  public void isCourseFullTest_true() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  public void isCourseFullTest_false() {
    testCourse.setEnrolledStudentCount(240);
    assertFalse(testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}