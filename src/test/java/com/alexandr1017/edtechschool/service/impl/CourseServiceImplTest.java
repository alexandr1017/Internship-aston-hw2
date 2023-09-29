package com.alexandr1017.edtechschool.service.impl;

import com.alexandr1017.edtechschool.dao.CourseDao;
import com.alexandr1017.edtechschool.dto.CourseDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;
import com.alexandr1017.edtechschool.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {

    private final CourseDao courseDao = Mockito.mock(CourseDao.class);
    private final CourseService courseService = CourseServiceImpl.getInstance(courseDao);

    @Test
    void findAll() {

        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = Arrays.asList(course1, course2);

        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> students1 = Arrays.asList(student1);
        List<Student> students2 = Arrays.asList(student2);

        when(courseDao.findAll()).thenReturn(courses);
        when(courseDao.findStudentsOnCourse(course1)).thenReturn(students1);
        when(courseDao.findStudentsOnCourse(course2)).thenReturn(students2);

        List<CourseDto> result = courseService.findAll();

        assertEquals(2, result.size());

        CourseDto dto1 = result.get(0);
        assertEquals(1, dto1.getStudentDtos().size());

        CourseDto dto2 = result.get(1);
        assertEquals(1, dto2.getStudentDtos().size());

    }

    @Test
    void getCourseById() {
        int courseId = 1;
        Course course = new Course();
        Student student1 = new Student();
        List<Student> students = Arrays.asList(student1);

        when(courseDao.getCourseById(courseId)).thenReturn(course);
        when(courseDao.findStudentsOnCourse(course)).thenReturn(students);

        CourseDto result = courseService.getCourseById(courseId);

        assertNotNull(result);
        assertEquals(course.getId(), result.getId());
        assertEquals(course.getName(), result.getName());
        assertEquals(course.getDuration(), result.getDuration());
        assertEquals(course.getPrice(), result.getPrice());
        assertEquals(1, result.getStudentDtos().size());
    }

    @Test
    void addCourse() {
        CourseDto courseDto = new CourseDto();
        courseDto.setName("Test Course");
        courseDto.setDuration(10);
        courseDto.setPrice(1000);

        courseService.addCourse(courseDto);

        ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
        Mockito.verify(courseDao).addCourse(courseCaptor.capture());
        Course capturedCourse = courseCaptor.getValue();

        assertEquals(courseDto.getName(), capturedCourse.getName());
        assertEquals(courseDto.getDuration(), capturedCourse.getDuration());
        assertEquals(courseDto.getPrice(), capturedCourse.getPrice());
    }

    @Test
    void updateCourse() {
        int courseId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setName("Test Course");
        courseDto.setDuration(10);
        courseDto.setPrice(1000);

        Course course = new Course();
        when(courseDao.getCourseById(courseId)).thenReturn(course);

        courseService.updateCourse(courseDto);

        ArgumentCaptor<Course> courseCaptor = ArgumentCaptor.forClass(Course.class);
        Mockito.verify(courseDao).updateCourse(courseCaptor.capture());
        Course capturedCourse = courseCaptor.getValue();

        assertEquals(courseDto.getId(), capturedCourse.getId());
        assertEquals(courseDto.getName(), capturedCourse.getName());
        assertEquals(courseDto.getDuration(), capturedCourse.getDuration());
        assertEquals(courseDto.getPrice(), capturedCourse.getPrice());
    }

    @Test
    void updateCourse_notFound() {
        int courseId = 1;
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);

        when(courseDao.getCourseById(courseId)).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> courseService.updateCourse(courseDto));
    }

    @Test
    void deleteCourseById() {
        int courseId = 1;
        Course course = new Course();

        when(courseDao.getCourseById(courseId)).thenReturn(course);

        courseService.deleteCourseById(courseId);

        Mockito.verify(courseDao).deleteCourseById(courseId);
    }

    @Test
    void deleteCourseById_notFound() {
        int courseId = 1;

        when(courseDao.getCourseById(courseId)).thenReturn(null);

        assertThrows(ItemNotFoundException.class, () -> courseService.deleteCourseById(courseId));
    }
}