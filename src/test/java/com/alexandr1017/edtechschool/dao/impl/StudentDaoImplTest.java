//package com.alexandr1017.edtechschool.dao.impl;
//
//import com.alexandr1017.edtechschool.dao.AbstractDaoTest;
//import com.alexandr1017.edtechschool.model.Course;
//import com.alexandr1017.edtechschool.model.Student;
//import org.junit.jupiter.api.*;
//
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class StudentDaoImplTest extends AbstractDaoTest {
//
//
//    private CourseDaoImpl courseDao;
//    private StudentDaoImpl studentDao;
//    private ManagementDaoImpl managementDao;
//
//
//    @BeforeEach
//    public void init() throws SQLException {
//        courseDao = new CourseDaoImpl(AbstractDaoTest.getDataSource());
//        studentDao = new StudentDaoImpl(AbstractDaoTest.getDataSource());
//        managementDao = new ManagementDaoImpl(AbstractDaoTest.getDataSource());
//
//    }
//
//
//    @Test
//    @Order(1)
//    void findCoursesForStudentTest() {
//        Student student = new Student();
//        student.setId(1);
//        student.setName("Test Student");
//        student.setAge(20);
//        student.setRegistrationDate(LocalDate.now());
//        studentDao.addStudent(student);
//
//        ArrayList<Course> courses = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            Course course = new Course();
//            course.setId(i);
//            course.setName("Test Course " + i);
//            course.setDuration(i * 2);
//            course.setPrice(i * 10000);
//            course.setCreatingDate(LocalDate.now());
//
//            courses.add(course);
//
//            courseDao.addCourse(course);
//            managementDao.addStudentToCourse(student.getId(), course.getId());
//        }
//
//        List<Course> coursesForStudent = studentDao.findCoursesForStudent(student);
//
//        assertNotNull(coursesForStudent);
//        assertEquals(courses.size(), coursesForStudent.size());
//        for (int i = 0; i < courses.size(); i++) {
//            assertEquals(courses.get(i).getId(), coursesForStudent.get(i).getId());
//            assertEquals(courses.get(i).getName(), coursesForStudent.get(i).getName());
//            assertEquals(courses.get(i).getDuration(), coursesForStudent.get(i).getDuration());
//            assertEquals(courses.get(i).getCreatingDate(), coursesForStudent.get(i).getCreatingDate());
//        }
//
//    }
//
//    @Test
//    @Order(2)
//    void addAndGetStudentTest() {
//        Student student = new Student();
//        student.setId(2);
//        student.setName("Test Student 2");
//        student.setAge(40);
//        student.setRegistrationDate(LocalDate.now());
//
//        studentDao.addStudent(student);
//
//        Student studentById = studentDao.findStudentById(2);
//
//        assertNotNull(studentById);
//        assertEquals(student.getId(), studentById.getId());
//        assertEquals(student.getName(), studentById.getName());
//        assertEquals(student.getAge(), studentById.getAge());
//        assertEquals(student.getRegistrationDate(), studentById.getRegistrationDate());
//
//    }
//
//    @Test
//    @Order(3)
//    void findAllTest() {
//        ArrayList<Student> students = new ArrayList<>();
//        for (int i = 1; i <= 2; i++) {
//            Student student = new Student();
//            student.setId(i);
//            student.setName("Test Student " + i);
//            student.setAge(i * 20);
//            student.setRegistrationDate(LocalDate.now());
//
//            students.add(student);
//            studentDao.updateStudent(student);
//        }
//
//        List<Student> findStudents = studentDao.findAll();
//        assertEquals(students, findStudents);
//    }
//
//    @Test
//    @Order(4)
//    void updateStudentTest() {
//        Student student = new Student();
//        student.setId(1);
//        student.setName("Updated Test Student");
//        student.setAge(40);
//        student.setRegistrationDate(LocalDate.now());
//
//        studentDao.updateStudent(student);
//
//        Student updatedStudent = studentDao.findStudentById(1);
//
//        assertNotNull(updatedStudent);
//        assertEquals(student.getId(), updatedStudent.getId());
//        assertEquals(student.getName(), updatedStudent.getName());
//        assertEquals(student.getAge(), updatedStudent.getAge());
//        assertEquals(student.getRegistrationDate(), updatedStudent.getRegistrationDate());
//
//    }
//
//
//    @Test
//    @Order(5)
//    void deleteStudentByIdTest() {
//        List<Student> all = studentDao.findAll();
//        int deleteId = 1;
//        studentDao.deleteStudentById(deleteId);
//        List<Student> onDelete = studentDao.findAll();
//        assertEquals(all.size() - 1, onDelete.size());
//
//        Student deleteStudent = studentDao.findStudentById(deleteId);
//        assertNull(deleteStudent);
//    }
//
//
//}