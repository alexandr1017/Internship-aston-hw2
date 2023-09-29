package com.alexandr1017.edtechschool.dao.impl;

import com.alexandr1017.edtechschool.dao.AbstractDaoTest;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;
import com.alexandr1017.edtechschool.model.Teacher;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManagementDaoImplTest extends AbstractDaoTest {

    private CourseDaoImpl courseDao;
    private StudentDaoImpl studentDao;
    private TeacherDaoImpl teacherDao;
    private ManagementDaoImpl managementDao;


    @BeforeEach
    public void init() throws SQLException {
        Connection connection = DriverManager.getConnection(mysqlContainer.getJdbcUrl(), mysqlContainer.getUsername(), mysqlContainer.getPassword());
        courseDao = new CourseDaoImpl(connection);
        studentDao = new StudentDaoImpl(connection);
        teacherDao = new TeacherDaoImpl(connection);

        managementDao = new ManagementDaoImpl(connection);

    }


    @Test
    @Order(1)
    void replaceTeacherOnCourseTest() {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("Test teacher 1");
        teacher.setAge(40);
        teacher.setHireDate(LocalDate.now());
        teacherDao.addTeacher(teacher);


        Course course = new Course();
        course.setId(1);
        course.setName("Test Course 1");
        course.setDuration(2);
        course.setPrice(10000);
        course.setCreatingDate(LocalDate.now());


        courseDao.addCourse(course);
        managementDao.addTeacherToCourse(teacher.getId(), course.getId());


        Teacher teacherTwo = new Teacher();
        teacherTwo.setId(2);
        teacherTwo.setName("Test teacher 2");
        teacherTwo.setAge(50);
        teacherTwo.setHireDate(LocalDate.now());
        teacherDao.addTeacher(teacherTwo);

        managementDao.replaceTeacherOnCourse(teacherTwo.getId(), course.getId());

    }

    @Test
    @Order(2)
    void removeStudentFromCourseTest() {
        Student student = new Student();
        student.setId(1);
        student.setName("Test Student");
        student.setAge(20);
        student.setRegistrationDate(LocalDate.now());
        studentDao.addStudent(student);

        Course course = new Course();
        course.setId(2);
        course.setName("Test Course 1");
        course.setDuration(5);
        course.setPrice(50000);
        course.setCreatingDate(LocalDate.now());
        courseDao.addCourse(course);


        List<Course> coursesBeforeAdd = studentDao.findCoursesForStudent(student);
        managementDao.addStudentToCourse(student.getId(), course.getId());


        managementDao.removeStudentFromCourse(student.getId(), course.getId());
        List<Course> coursesAfterRemove = studentDao.findCoursesForStudent(student);

        assertEquals(coursesBeforeAdd, coursesAfterRemove);

    }
}