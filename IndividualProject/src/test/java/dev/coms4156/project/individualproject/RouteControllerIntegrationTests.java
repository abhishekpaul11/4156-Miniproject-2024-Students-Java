package dev.coms4156.project.individualproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This is an integration test class for all the endpoints in RouteController class.
 */
@WebMvcTest(RouteController.class)
public class RouteControllerIntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  private static MyFileDatabase initialDatabase;

  @BeforeAll
  public static void startApplication() {
    IndividualProjectApplication.main(new String[]{"setup"});
    initialDatabase = IndividualProjectApplication.myFileDatabase.duplicate();
  }

  @AfterAll
  public static void stopApplication() {
    IndividualProjectApplication.stopApplication();
  }

  @BeforeEach
  public void setUp() {
    IndividualProjectApplication.overrideDatabase(initialDatabase.duplicate());
  }

  @Test
  public void retrieveDepartmentTest_with_valid_department() throws Exception {
    mockMvc.perform(get("/retrieveDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string(
        "COMS 3827: \n" + "Instructor: Daniel Rubenstein; Location: "
        + "207 Math; Time: 10:10-11:25\n" + "COMS 1004: \n" + "Instructor: Adam Cannon; "
        + "Location: 417 IAB; Time: 11:40-12:55\n" + "COMS 3203: \n" + "Instructor: Ansaf "
        + "Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25\n" + "COMS 4156: \n"
        + "Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25\n"
        + "COMS 3157: \n" + "Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25\n"
        + "COMS 3134: \n" + "Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n"
        + "COMS 3251: \n" + "Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40\n"
        + "COMS 3261: \n" + "Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55\n"));
  }

  @Test
  public void retrieveDepartmentTest_with_invalid_department() throws Exception {
    mockMvc.perform(get("/retrieveDept").param("deptCode", "ENG"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Department Not Found")));
  }

  @Test
  public void retrieveCourseTest_with_valid_course() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", "COMS")
        .param("courseCode", "3134"))
        .andExpect(status().isOk())
        .andExpect(content().string(
          "\nInstructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25"
        ));
  }

  @Test
  public void retrieveCourseTest_with_invalid_department() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "ABCD")
            .param("courseCode", "3000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Department Not Found")));
  }

  @Test
  public void retrieveCourseTest_with_invalid_course() throws Exception {
    mockMvc.perform(get("/retrieveCourse")
        .param("deptCode", "ECON")
        .param("courseCode", "3000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void isCourseFullTest_with_full_course() throws Exception {
    mockMvc.perform(get("/isCourseFull")
        .param("deptCode", "PSYC")
        .param("courseCode", "2235"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("true")));
  }

  @Test
  public void isCourseFullTest_with_course_with_vacancy() throws Exception {
    mockMvc.perform(get("/isCourseFull")
        .param("deptCode", "PSYC")
        .param("courseCode", "1610"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("false")));
  }

  @Test
  public void isCourseFullTest_with_invalid_course() throws Exception {
    mockMvc.perform(get("/isCourseFull")
        .param("deptCode", "PSYC")
        .param("courseCode", "1000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void getMajorCountFromDepartmentTest_with_valid_department() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
        .param("deptCode", "PSYC"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("There are: 437 majors in the department")));
  }

  @Test
  public void getMajorCountFromDepartmentTest_with_invalid_department() throws Exception {
    mockMvc.perform(get("/getMajorCountFromDept")
        .param("deptCode", "ABCD"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Department Not Found")));
  }

  @Test
  public void identifyDepartmentChairTest_with_valid_department() throws Exception {
    mockMvc.perform(get("/idDeptChair")
        .param("deptCode", "PSYC"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Nim Tottenham is the department chair.")));
  }

  @Test
  public void identifyDepartmentChairTest_with_invalid_department() throws Exception {
    mockMvc.perform(get("/idDeptChair")
        .param("deptCode", "ABCD"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Department Not Found")));
  }

  @Test
  public void findCourseLocationTest_with_valid_course() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
        .param("deptCode", "COMS")
        .param("courseCode", "3157"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("417 IAB is where the course is located.")));
  }

  @Test
  public void findCourseLocationTest_with_invalid_course() throws Exception {
    mockMvc.perform(get("/findCourseLocation")
        .param("deptCode", "ABCD")
        .param("courseCode", "3000"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void findCourseInstructoTestr_with_valid_course() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
        .param("deptCode", "COMS")
        .param("courseCode", "3157"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Jae Lee is the instructor for the course.")));
  }

  @Test
  public void findCourseInstructorTest_with_invalid_course() throws Exception {
    mockMvc.perform(get("/findCourseInstructor")
        .param("deptCode", "DEFG")
        .param("courseCode", "5678"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void findCourseTimeTest_with_valid_course() throws Exception {
    mockMvc.perform(get("/findCourseTime")
        .param("deptCode", "COMS")
        .param("courseCode", "3157"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("The course meets at: 4:10-5:25")));
  }

  @Test
  public void findCourseTimeTest_with_invalid_course() throws Exception {
    mockMvc.perform(get("/findCourseTime")
        .param("deptCode", "PHYS")
        .param("courseCode", "2134"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void addMajorToDepartmentTest_with_valid_department() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
        .param("deptCode", "ECON"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Attribute was updated successfully")));
  }

  @Test
  public void addMajorToDepartmentTest_with_invalid_department() throws Exception {
    mockMvc.perform(patch("/addMajorToDept")
        .param("deptCode", "QWER"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Department Not Found")));
  }

  @Test
  public void removeMajorFromDepartmentTest_with_valid_department() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
        .param("deptCode", "IEOR"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Attribute was updated or is at minimum")));
  }

  @Test
  public void removeMajorFromDepartmentTest_with_invalid_department() throws Exception {
    mockMvc.perform(patch("/removeMajorFromDept")
        .param("deptCode", "HEHE"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Department Not Found")));
  }

  @Test
  public void dropStudentFromCourseTest_success() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", "COMS")
        .param("courseCode", "3134"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Student has been dropped.")));
  }

  @Test
  public void dropStudentFromCourseTest_failure() throws Exception {
    // removing all students from course to simulate failure case
    Map<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    Map<String, Course> coursesMapping;
    coursesMapping = departmentMapping.get("COMS").getCourseSelection();

    Course requestedCourse = coursesMapping.get("3157");
    requestedCourse.setEnrolledStudentCount(0);

    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", "COMS")
        .param("courseCode", "3157"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(Matchers.is("Student has not been dropped.")));
  }

  @Test
  public void dropStudentFromCourseTest_with_invalid_course() throws Exception {
    mockMvc.perform(patch("/dropStudentFromCourse")
        .param("deptCode", "COMS")
        .param("courseCode", "7090"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void setEnrollmentCountTest_with_valid_course() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", "IEOR")
        .param("courseCode", "4102")
        .param("count", "50"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Attribute was updated successfully.")));
  }

  @Test
  public void setEnrollmentCountTest_with_invalid_course() throws Exception {
    mockMvc.perform(patch("/setEnrollmentCount")
        .param("deptCode", "COMS")
        .param("courseCode", "7090")
        .param("count", "50"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void changeCourseTimeTest_with_valid_course() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
        .param("deptCode", "IEOR")
        .param("courseCode", "4102")
        .param("time", "4:15-5:45"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Attribute was updated successfully.")));
  }

  @Test
  public void changeCourseTimeTest_with_invalid_course() throws Exception {
    mockMvc.perform(patch("/changeCourseTime")
        .param("deptCode", "COMS")
        .param("courseCode", "7090")
        .param("time", "4:15-5:45"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void changeCourseTeacherTest_with_valid_course() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
        .param("deptCode", "IEOR")
        .param("courseCode", "4102")
        .param("teacher", "Gail Kaiser"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Attribute was updated successfully.")));
  }

  @Test
  public void changeCourseTeacherTest_with_invalid_course() throws Exception {
    mockMvc.perform(patch("/changeCourseTeacher")
        .param("deptCode", "COMS")
        .param("courseCode", "7090")
        .param("teacher", "Gail Kaiser"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

  @Test
  public void changeCourseLocationTest_with_valid_course() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
        .param("deptCode", "IEOR")
        .param("courseCode", "4102")
        .param("location", "833 MUD"))
        .andExpect(status().isOk())
        .andExpect(content().string(Matchers.is("Attribute was updated successfully.")));
  }

  @Test
  public void changeCourseLocationTest_with_invalid_course() throws Exception {
    mockMvc.perform(patch("/changeCourseLocation")
        .param("deptCode", "COMS")
        .param("courseCode", "7090")
        .param("location", "833 MUD"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(Matchers.is("Course Not Found")));
  }

}
