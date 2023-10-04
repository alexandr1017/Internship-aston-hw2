//package com.alexandr1017.edtechschool.dao.impl;
//
//import com.alexandr1017.edtechschool.dao.AbstractDaoTest;
//import com.alexandr1017.edtechschool.model.Course;
//import com.alexandr1017.edtechschool.model.Teacher;
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
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class TeacherDaoImplTest extends AbstractDaoTest {
//
//    private CourseDaoImpl courseDao;
//    private ManagementDaoImpl managementDao;
//    private TeacherDaoImpl teacherDao;
//
//    @BeforeEach
//    public void init() throws SQLException {
//        courseDao = new CourseDaoImpl(AbstractDaoTest.getDataSource());
//        teacherDao = new TeacherDaoImpl(AbstractDaoTest.getDataSource());
//        managementDao = new ManagementDaoImpl(AbstractDaoTest.getDataSource());
//    }
//
//    @Test
//    @Order(1)
//    void findCoursesForTeacher() {
//        Teacher teacher = new Teacher();
//        teacher.setId(1);
//        teacher.setName("Test teacher");
//        teacher.setAge(40);
//        teacher.setHireDate(LocalDate.now());
//        teacherDao.addTeacher(teacher);
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
//            managementDao.addTeacherToCourse(teacher.getId(), course.getId());
//        }
//
//        List<Course> coursesForTeacher = teacherDao.findCoursesForTeacher(teacher);
//
//        assertNotNull(coursesForTeacher);
//        assertEquals(courses.size(), coursesForTeacher.size());
//
//        for (int i = 0; i < courses.size(); i++) {
//            assertEquals(courses.get(i).getId(), coursesForTeacher.get(i).getId());
//            assertEquals(courses.get(i).getName(), coursesForTeacher.get(i).getName());
//            assertEquals(courses.get(i).getDuration(), coursesForTeacher.get(i).getDuration());
//            assertEquals(courses.get(i).getCreatingDate(), coursesForTeacher.get(i).getCreatingDate());
//        }
//    }
//
//    @Test
//    @Order(2)
//    void addAndFindTeacher() {
//        Teacher teacher = new Teacher();
//        teacher.setId(2);
//        teacher.setName("Test teacher 2");
//        teacher.setAge(33);
//        teacher.setHireDate(LocalDate.now());
//
//        teacherDao.addTeacher(teacher);
//
//        Teacher teacherById = teacherDao.findTeacherById(2);
//
//        assertNotNull(teacherById);
//        assertEquals(teacher.getId(), teacherById.getId());
//        assertEquals(teacher.getName(), teacherById.getName());
//        assertEquals(teacher.getAge(), teacherById.getAge());
//        assertEquals(teacher.getHireDate(), teacherById.getHireDate());
//
//    }
//
//    @Test
//    @Order(3)
//    void findAll() {
//        ArrayList<Teacher> teachers = new ArrayList<>();
//        for (int i = 1; i <= 2; i++) {
//            Teacher teacher = new Teacher();
//            teacher.setId(i);
//            teacher.setName("Test Student " + i);
//            teacher.setAge(i * 20);
//            teacher.setHireDate(LocalDate.now());
//
//            teachers.add(teacher);
//            teacherDao.updateTeacher(teacher);
//        }
//
//        List<Teacher> findTeachers = teacherDao.findAll();
//        assertEquals(teachers, findTeachers);
//    }
//
//    @Test
//    @Order(4)
//    void updateTeacher() {
//        Teacher teacher = new Teacher();
//        teacher.setId(1);
//        teacher.setName("Updated Test teacher");
//        teacher.setAge(50);
//        teacher.setHireDate(LocalDate.now());
//
//        teacherDao.updateTeacher(teacher);
//
//        Teacher updatedTeacher = teacherDao.findTeacherById(1);
//
//        assertNotNull(updatedTeacher);
//        assertEquals(teacher.getId(), updatedTeacher.getId());
//        assertEquals(teacher.getName(), updatedTeacher.getName());
//        assertEquals(teacher.getAge(), updatedTeacher.getAge());
//        assertEquals(teacher.getHireDate(), updatedTeacher.getHireDate());
//
//    }
//
//
//    @Test
//    @Order(5)
//    void deleteTeacher() {
//        List<Teacher> all = teacherDao.findAll();
//        int deleteId = 1;
//        teacherDao.deleteTeacher(deleteId);
//        List<Teacher> onDelete = teacherDao.findAll();
//
//        assertEquals(all.size() - 1, onDelete.size());
//
//        Teacher deleteTeacher = teacherDao.findTeacherById(deleteId);
//        assertNull(deleteTeacher);
//    }
//
//
//}