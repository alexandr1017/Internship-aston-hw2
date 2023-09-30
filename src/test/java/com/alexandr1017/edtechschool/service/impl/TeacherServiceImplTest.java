package com.alexandr1017.edtechschool.service.impl;


import com.alexandr1017.edtechschool.dao.TeacherDao;
import com.alexandr1017.edtechschool.dto.TeacherDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TeacherServiceImplTest {

    private  TeacherDao teacherDao;
    private  TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        teacherDao = Mockito.mock(TeacherDao.class);
        teacherService = TeacherServiceImpl.getInstance();
        teacherService.setTeacherDao(teacherDao);
    }
    @Test
    void findAll() {
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);

        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses1 = Arrays.asList(course1);
        List<Course> courses2 = Arrays.asList(course2);

        when(teacherDao.findAll()).thenReturn(teachers);
        when(teacherDao.findCoursesForTeacher(teacher1)).thenReturn(courses1);
        when(teacherDao.findCoursesForTeacher(teacher2)).thenReturn(courses2);

        List<TeacherDto> result = teacherService.findAll();

        assertEquals(2, result.size());

        TeacherDto dto1 = result.get(0);
        assertEquals(1, dto1.getCourses().size());

        TeacherDto dto2 = result.get(1);
        assertEquals(1, dto2.getCourses().size());
    }

    @Test
    void findTeacherById() {
        int teacherId = 1;
        Teacher teacher = new Teacher();

        Course course1 = new Course();
        List<Course> courses = Arrays.asList(course1);

        when(teacherDao.findTeacherById(teacherId)).thenReturn(teacher);
        when(teacherDao.findCoursesForTeacher(teacher)).thenReturn(courses);

        TeacherDto result = teacherService.findTeacherById(teacherId);

        assertNotNull(result);
        assertEquals(teacher.getId(), result.getId());
        assertEquals(teacher.getName(), result.getName());
        assertEquals(teacher.getAge(), result.getAge());
        assertEquals(1, result.getCourses().size());
    }

    @Test
    void findTeacherById_notFound() {
        int teacherId = 1;

        when(teacherDao.findTeacherById(teacherId)).thenReturn(null);

        TeacherDto result = teacherService.findTeacherById(teacherId);

        assertNull(result);
    }

    @Test
    void addTeacher() {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setName("Test Teacher");
        teacherDto.setAge(30);

        teacherService.addTeacher(teacherDto);

        ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
        Mockito.verify(teacherDao).addTeacher(teacherCaptor.capture());
        Teacher capturedTeacher = teacherCaptor.getValue();

        assertEquals(teacherDto.getName(), capturedTeacher.getName());
        assertEquals(teacherDto.getAge(), capturedTeacher.getAge());
    }

    @Test
    void updateTeacher() {
        int teacherId = 1;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacherId);
        teacherDto.setName("Test Teacher");
        teacherDto.setAge(30);

        Teacher teacher = new Teacher();
        when(teacherDao.findTeacherById(teacherId)).thenReturn(teacher);

        teacherService.updateTeacher(teacherDto);

        ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
        Mockito.verify(teacherDao).updateTeacher(teacherCaptor.capture());
        Teacher capturedTeacher = teacherCaptor.getValue();

        assertEquals(teacherDto.getId(), capturedTeacher.getId());
        assertEquals(teacherDto.getName(), capturedTeacher.getName());
        assertEquals(teacherDto.getAge(), capturedTeacher.getAge());
    }

    @Test
    void updateTeacher_notFound() {
        int teacherId = 1;
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacherId);

        when(teacherDao.findTeacherById(teacherId)).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> teacherService.updateTeacher(teacherDto));
    }

    @Test
    void deleteTeacher() {
        int teacherId = 1;
        Teacher teacher = new Teacher();

        when(teacherDao.findTeacherById(teacherId)).thenReturn(teacher);

        teacherService.deleteTeacher(teacherId);

        Mockito.verify(teacherDao).deleteTeacher(teacherId);
    }

    @Test
    void deleteTeacher_notFound() {
        int teacherId = 1;

        when(teacherDao.findTeacherById(teacherId)).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> teacherService.deleteTeacher(teacherId));
    }
}