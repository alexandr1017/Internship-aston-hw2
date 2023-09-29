package com.alexandr1017.edtechschool.dao.impl;

import com.alexandr1017.edtechschool.dao.AbstractDaoTest;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseDaoImplTest extends AbstractDaoTest {

    private CourseDaoImpl courseDao;
    private StudentDaoImpl studentDao;
    private ManagementDaoImpl managementDao;

    @BeforeEach
    public void init() throws SQLException {
        Connection connection = DriverManager.getConnection(mysqlContainer.getJdbcUrl(), mysqlContainer.getUsername(), mysqlContainer.getPassword());
        courseDao = new CourseDaoImpl(connection);
        studentDao = new StudentDaoImpl(connection);
        managementDao = new ManagementDaoImpl(connection);

    }


    @Test
    @Order(1)
    public void testFindStudentsOnCourse() {
        Course course = new Course();
        course.setId(1);
        course.setName("Test Course");
        course.setDuration(10);
        course.setPrice(100);
        course.setCreatingDate(LocalDate.now());
        courseDao.addCourse(course);

        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Student student = new Student();
            student.setId(i);
            student.setName("Test Student " + i);
            student.setAge(i * 10);
            student.setRegistrationDate(LocalDate.now());

            students.add(student);
            studentDao.addStudent(student);
            managementDao.addStudentToCourse(student.getId(), course.getId());
        }

        List<Student> retrievedStudents = courseDao.findStudentsOnCourse(course);

        assertNotNull(retrievedStudents);
        assertEquals(students.size(), retrievedStudents.size());
        for (int i = 0; i < students.size(); i++) {
            assertEquals(students.get(i).getId(), retrievedStudents.get(i).getId());
            assertEquals(students.get(i).getName(), retrievedStudents.get(i).getName());
            assertEquals(students.get(i).getAge(), retrievedStudents.get(i).getAge());
            assertEquals(students.get(i).getRegistrationDate(), retrievedStudents.get(i).getRegistrationDate());
        }
    }

    @Test
    @Order(2)
    public void testAddAndGetCourse() {
        Course course = new Course();
        course.setId(2);
        course.setName("Test Course");
        course.setDuration(10);
        course.setPrice(100);
        course.setCreatingDate(LocalDate.now());

        courseDao.addCourse(course);

        Course retrievedCourse = courseDao.getCourseById(2);

        assertNotNull(retrievedCourse);
        assertEquals(course.getId(), retrievedCourse.getId());
        assertEquals(course.getName(), retrievedCourse.getName());
        assertEquals(course.getDuration(), retrievedCourse.getDuration());
        assertEquals(course.getPrice(), retrievedCourse.getPrice());
        assertEquals(course.getCreatingDate(), retrievedCourse.getCreatingDate());
    }

    @Test
    @Order(3)
    public void testFindAll() {
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Course course = new Course();
            course.setId(i);
            course.setName("Test Course " + i);
            course.setDuration(i * 10);
            course.setPrice(i * 100);
            course.setCreatingDate(LocalDate.now());

            courses.add(course);
            courseDao.updateCourse(course);
        }
        List<Course> findCourses = courseDao.findAll();
        assertEquals(courses, findCourses);
    }


    @Test
    @Order(4)
    public void updateCourseTest() {
        Course course = new Course();
        course.setId(1);
        course.setName("Updated Test Course");
        course.setDuration(20);
        course.setPrice(200);
        course.setCreatingDate(LocalDate.now());

        courseDao.updateCourse(course);

        Course updatedCourse = courseDao.getCourseById(1);

        assertNotNull(updatedCourse);
        assertEquals(course.getId(), updatedCourse.getId());
        assertEquals(course.getName(), updatedCourse.getName());
        assertEquals(course.getDuration(), updatedCourse.getDuration());
        assertEquals(course.getPrice(), updatedCourse.getPrice());
        assertEquals(course.getCreatingDate(), updatedCourse.getCreatingDate());
    }


    @Test
    @Order(5)
    public void deleteCourseByIdTest() {
        List<Course> all = courseDao.findAll();
        int deleteId = 1;
        courseDao.deleteCourseById(deleteId);
        List<Course> onDelete = courseDao.findAll();
        assertEquals(all.size() - 1, onDelete.size());


        Course deletedCourse = courseDao.getCourseById(deleteId);
        assertNull(deletedCourse);
    }


}
