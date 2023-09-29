package com.alexandr1017.edtechschool.service;

import com.alexandr1017.edtechschool.dto.TeacherDto;
import com.alexandr1017.edtechschool.model.Student;
import com.alexandr1017.edtechschool.model.Teacher;

import java.util.List;

public interface TeacherService {
    List<TeacherDto> findAll();
    void deleteTeacher(int teacherId);
    void updateTeacher(TeacherDto teacherDto);
    TeacherDto findTeacherById(int teacherId);
    void addTeacher(TeacherDto teacherDto);
}
