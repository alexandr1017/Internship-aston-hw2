package com.alexandr1017.edtechschool.service.impl;



import com.alexandr1017.edtechschool.dao.StudentDao;
import com.alexandr1017.edtechschool.dto.StudentDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    private StudentDao studentDao;
    private  StudentServiceImpl studentService;
    @BeforeEach
    void setUp() {
        studentDao = Mockito.mock(StudentDao.class);
        studentService = StudentServiceImpl.getInstance();
        studentService.setStudentDao(studentDao);
    }

    @Test
    void findAll() {
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> students = Arrays.asList(student1, student2);

        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses1 = Arrays.asList(course1);
        List<Course> courses2 = Arrays.asList(course2);

        when(studentDao.findAll()).thenReturn(students);
        when(studentDao.findCoursesForStudent(student1)).thenReturn(courses1);
        when(studentDao.findCoursesForStudent(student2)).thenReturn(courses2);

        List<StudentDto> result = studentService.findAll();

        assertEquals(2, result.size());

        StudentDto dto1 = result.get(0);
        assertEquals(1, dto1.getCourseDtos().size());

        StudentDto dto2 = result.get(1);
        assertEquals(1, dto2.getCourseDtos().size());
    }

    @Test
    void getStudentById() {
        int studentId = 1;
        Student student = new Student();

        Course course1 = new Course();
        List<Course> courses = Arrays.asList(course1);

        when(studentDao.findStudentById(studentId)).thenReturn(student);
        when(studentDao.findCoursesForStudent(student)).thenReturn(courses);

        StudentDto result = studentService.getStudentById(studentId);

        assertNotNull(result);
        assertEquals(student.getId(), result.getId());
        assertEquals(student.getName(), result.getName());
        assertEquals(student.getAge(), result.getAge());
        assertEquals(1, result.getCourseDtos().size());
    }

    @Test
    void getStudentById_notFound() {
        int studentId = 1;

        when(studentDao.findStudentById(studentId)).thenReturn(null);

        StudentDto result = studentService.getStudentById(studentId);

        assertNull(result);
    }

    @Test
    void addStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Test Student");
        studentDto.setAge(20);

        studentService.addStudent(studentDto);

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentDao).addStudent(studentCaptor.capture());
        Student capturedStudent = studentCaptor.getValue();

        assertEquals(studentDto.getName(), capturedStudent.getName());
        assertEquals(studentDto.getAge(), capturedStudent.getAge());
    }


    @Test
    void updateStudent() {
        int studentId = 1;
        StudentDto studentDto = new StudentDto();
        studentDto.setId(studentId);
        studentDto.setName("Test Student");
        studentDto.setAge(20);

        Student student = new Student();
        when(studentDao.findStudentById(studentId)).thenReturn(student);

        studentService.updateStudent(studentDto);

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentDao).updateStudent(studentCaptor.capture());
        Student capturedStudent = studentCaptor.getValue();

        assertEquals(studentDto.getId(), capturedStudent.getId());
        assertEquals(studentDto.getName(), capturedStudent.getName());
        assertEquals(studentDto.getAge(), capturedStudent.getAge());
    }

    @Test
    void updateStudent_notFound() {
        int studentId = 1;
        StudentDto studentDto = new StudentDto();
        studentDto.setId(studentId);

        when(studentDao.findStudentById(studentId)).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> studentService.updateStudent(studentDto));
    }

    @Test
    void deleteStudentById() {
        int studentId = 1;
        Student student = new Student();

        when(studentDao.findStudentById(studentId)).thenReturn(student);

        studentService.deleteStudentById(studentId);

        Mockito.verify(studentDao).deleteStudentById(studentId);
    }

    @Test
    void deleteStudentById_notFound() {
        int studentId = 1;

        when(studentDao.findStudentById(studentId)).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> studentService.deleteStudentById(studentId));
    }
}