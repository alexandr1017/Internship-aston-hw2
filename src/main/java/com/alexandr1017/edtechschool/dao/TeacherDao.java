package com.alexandr1017.edtechschool.dao;


import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Teacher;

import java.util.List;

public interface TeacherDao {
    List<Teacher> findAll();
    void deleteTeacher(int teacherId);
    void updateTeacher(Teacher teacher);
    Teacher findTeacherById(int teacherId);
    void addTeacher(Teacher teacher);

    List<Course> findCoursesForTeacher(Teacher teacher);
}

