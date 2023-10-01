package com.alexandr1017.edtechschool.service.impl;


import com.alexandr1017.edtechschool.dao.StudentDao;


import com.alexandr1017.edtechschool.dao.impl.StudentDaoImpl;
import com.alexandr1017.edtechschool.dto.CourseDto;
import com.alexandr1017.edtechschool.dto.StudentDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;

import com.alexandr1017.edtechschool.service.StudentService;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private static final StudentServiceImpl INSTANCE = new StudentServiceImpl();
    private StudentDao studentDao = StudentDaoImpl.getInstance();


    private StudentServiceImpl() {
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }


    @Override
    public List<StudentDto> findAll() {
        return studentDao.findAll().stream()
                .map(student -> {
                            List<Course> collect = studentDao.findCoursesForStudent(student);
                            List<CourseDto> courseDtos = collect.stream().map(CourseDto::toDto).collect(Collectors.toList());
                            StudentDto dto = StudentDto.toDto(student);
                            dto.setCourseDtos(courseDtos);
                            return dto;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(int studentId) {
        Student studentById = studentDao.findStudentById(studentId);
        return studentById == null ? null : new StudentDto(
                studentById.getId(),
                studentById.getName(),
                studentById.getAge(),
                studentDao.findCoursesForStudent(studentById).stream().map(CourseDto::toDto).collect(Collectors.toList())
        );
    }

    @Override
    public void addStudent(StudentDto studentDto) {

        Student student = new Student();
        student.setName(studentDto.getName());
        student.setAge(studentDto.getAge());
        student.setRegistrationDate(LocalDate.now());

        studentDao.addStudent(student);
    }

    @Override
    public void updateStudent(StudentDto studentDto) {

        Student studentById = studentDao.findStudentById(studentDto.getId());
        if (studentById == null) {
            throw new ItemNotFoundException("Студент с таким Id: " + studentDto.getId() + " не найден");
        }

        Student student = new Student();
        student.setName(studentDto.getName());
        student.setAge(studentDto.getAge());
        student.setRegistrationDate(LocalDate.now());
        student.setId(studentDto.getId());

        studentDao.updateStudent(student);
    }

    @Override
    public void deleteStudentById(int studentId) {
        Student studentById = studentDao.findStudentById(studentId);
        if (studentById == null) {
            throw new ItemNotFoundException("Студент с таким Id: " + studentId + " не найден");
        }
        studentDao.deleteStudentById(studentId);
    }

    public static StudentServiceImpl getInstance() {
        return INSTANCE;
    }

}
