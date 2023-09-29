package com.alexandr1017.edtechschool.service.impl;


import com.alexandr1017.edtechschool.dao.TeacherDao;

import com.alexandr1017.edtechschool.dao.impl.TeacherDaoImpl;
import com.alexandr1017.edtechschool.dto.CourseDto;

import com.alexandr1017.edtechschool.dto.TeacherDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Teacher;

import com.alexandr1017.edtechschool.service.TeacherService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherServiceImpl implements TeacherService {

    private static final TeacherService INSTANCE = new TeacherServiceImpl();
    private TeacherDao teacherDao = TeacherDaoImpl.getInstance();

    private TeacherServiceImpl() {
    }

    private TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }


    @Override
    public List<TeacherDto> findAll() {
        return teacherDao.findAll().stream()
                .map(teacher -> {
                    List<Course> collect = teacherDao.findCoursesForTeacher(teacher);
                    List<CourseDto> courseDtos = collect.stream().map(CourseDto::toDto).collect(Collectors.toList());
                    TeacherDto teacherDto = TeacherDto.toDto(teacher);
                    teacherDto.setCourses(courseDtos);
                    return teacherDto;
                }).collect(Collectors.toList());
    }

    @Override
    public TeacherDto findTeacherById(int teacherId) {
        Teacher teacherById = teacherDao.findTeacherById(teacherId);

        return teacherById == null ? null : new TeacherDto(
                teacherById.getId(),
                teacherById.getName(),
                teacherById.getAge(),
                teacherDao.findCoursesForTeacher(teacherById).stream().map(CourseDto::toDto).collect(Collectors.toList())
        );
    }

    @Override
    public void addTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDto.getName());
        teacher.setAge(teacherDto.getAge());
        teacher.setHireDate(LocalDate.now());

        teacherDao.addTeacher(teacher);
    }

    @Override
    public void updateTeacher(TeacherDto teacherDto) {
        Teacher teacherById = teacherDao.findTeacherById(teacherDto.getId());
        if (teacherById == null) {
            throw new ItemNotFoundException("Преподаватель с таким Id " + teacherDto.getId() + " не найден");
        }
        Teacher teacher = new Teacher();
        teacher.setId(teacherDto.getId());
        teacher.setName(teacherDto.getName());
        teacher.setAge(teacherDto.getAge());
        teacher.setHireDate(LocalDate.now());

        teacherDao.updateTeacher(teacher);
    }

    @Override
    public void deleteTeacher(int teacherId) {
        Teacher teacherById = teacherDao.findTeacherById(teacherId);
        if (teacherById == null) {
            throw new ItemNotFoundException("Преподаватель с таким Id " + teacherId + " не найден");
        }
        teacherDao.deleteTeacher(teacherId);
    }


    public static TeacherService getInstance() {
        return INSTANCE;
    }

    public static TeacherService getInstance(TeacherDao teacherDao) {
        return new TeacherServiceImpl(teacherDao);
    }


}
