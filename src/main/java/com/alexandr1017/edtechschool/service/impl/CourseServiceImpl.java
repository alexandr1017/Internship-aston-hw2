package com.alexandr1017.edtechschool.service.impl;

import com.alexandr1017.edtechschool.dao.CourseDao;
import com.alexandr1017.edtechschool.dto.CourseDto;
import com.alexandr1017.edtechschool.dto.StudentDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;
import com.alexandr1017.edtechschool.service.CourseService;


import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {

    private static final CourseServiceImpl INSTANCE = new CourseServiceImpl();

    private CourseDao courseDao;

    private CourseServiceImpl() {
    }



    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }


    @Override
    public List<CourseDto> findAll() {

        return courseDao.findAll().stream()
                .map(course -> {
                            List<Student> collect = courseDao.findStudentsOnCourse(course);
                            List<StudentDto> studentDtos = collect.stream().map(StudentDto::toDto).collect(Collectors.toList());
                            CourseDto dto = CourseDto.toDto(course);
                            dto.setStudentDtos(studentDtos);
                            return dto;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public CourseDto getCourseById(int courseId) {

        Course courseById = courseDao.getCourseById(courseId);

        List<StudentDto> collectStudentsDto = courseDao.findStudentsOnCourse(courseById).stream().map(StudentDto::toDto).collect(Collectors.toList());
        return courseById == null ? null : new CourseDto(
                courseById.getId(),
                courseById.getName(),
                courseById.getDuration(),
                courseById.getPrice(),
                collectStudentsDto);
    }

    @Override
    public void addCourse(CourseDto courseDto) {

        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDuration(courseDto.getDuration());
        course.setPrice(courseDto.getPrice());
        course.setCreatingDate(LocalDate.now());

        courseDao.addCourse(course);
    }

    @Override
    public void updateCourse(CourseDto courseDto) {


        Course courseById = courseDao.getCourseById(courseDto.getId());
        if (courseById == null) {
            throw new ItemNotFoundException("Курс с таким Id: " + courseDto.getId() + " не найден");
        }

        Course course = new Course();
        course.setId(courseDto.getId());
        course.setName(courseDto.getName());
        course.setDuration(courseDto.getDuration());
        course.setPrice(courseDto.getPrice());
        course.setCreatingDate(LocalDate.now());

        courseDao.updateCourse(course);
    }

    @Override
    public void deleteCourseById(int courseId) {
        Course courseById = courseDao.getCourseById(courseId);
        if (courseById == null) {
            throw new ItemNotFoundException("Курс с таким Id: " + courseId + " не найден");
        }
        courseDao.deleteCourseById(courseId);
    }


    public static CourseServiceImpl getInstance() {
        return INSTANCE;
    }

}
