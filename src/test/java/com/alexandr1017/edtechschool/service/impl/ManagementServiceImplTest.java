package com.alexandr1017.edtechschool.service.impl;

import com.alexandr1017.edtechschool.dao.CourseDao;
import com.alexandr1017.edtechschool.dao.ManagementDao;
import com.alexandr1017.edtechschool.dao.StudentDao;
import com.alexandr1017.edtechschool.dao.TeacherDao;

import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;
import com.alexandr1017.edtechschool.model.Teacher;

import com.alexandr1017.edtechschool.service.ManagementService;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.when;

class ManagementServiceImplTest {


    private final CourseDao courseDao = Mockito.mock(CourseDao.class);

    private final StudentDao studentDao = Mockito.mock(StudentDao.class);

    private final TeacherDao teacherDao = Mockito.mock(TeacherDao.class);

    private final ManagementDao managementDao = Mockito.mock(ManagementDao.class);
    private final ManagementService managementService = ManagementServiceImpl.getInstance(managementDao);

    @Test
    void addStudentToCourse() {
        int studentId = 1;
        int courseId = 1;
        Student student = new Student();
        Course course = new Course();

        when(studentDao.findStudentById(studentId)).thenReturn(student);
        when(courseDao.getCourseById(courseId)).thenReturn(course);

        managementService.addStudentToCourse(studentId, courseId);

        Mockito.verify(managementDao).addStudentToCourse(studentId, courseId);
    }


    @Test
    void removeStudentFromCourse() {
        int studentId = 1;
        int courseId = 1;
        Student student = new Student();
        Course course = new Course();

        when(studentDao.findStudentById(studentId)).thenReturn(student);
        when(courseDao.getCourseById(courseId)).thenReturn(course);

        managementService.removeStudentFromCourse(studentId, courseId);

        Mockito.verify(managementDao).removeStudentFromCourse(studentId, courseId);
    }


    @Test
    void addTeacherToCourse() {
        int teacherId = 1;
        int courseId = 1;
        Teacher teacher = new Teacher();
        Course course = new Course();

        when(teacherDao.findTeacherById(teacherId)).thenReturn(teacher);
        when(courseDao.getCourseById(courseId)).thenReturn(course);

        managementService.addTeacherToCourse(teacherId, courseId);

        Mockito.verify(managementDao).addTeacherToCourse(teacherId, courseId);
    }


    @Test
    void replaceTeacherOnCourse() {
        int teacherId = 1;
        int courseId = 1;
        Teacher teacher = new Teacher();
        Course course = new Course();

        when(teacherDao.findTeacherById(teacherId)).thenReturn(teacher);
        when(courseDao.getCourseById(courseId)).thenReturn(course);

        managementService.replaceTeacherOnCourse(teacherId, courseId);

        Mockito.verify(managementDao).replaceTeacherOnCourse(teacherId, courseId);
    }

}