package com.alexandr1017.edtechschool.dao;

import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;

import java.util.List;

public interface StudentDao {


    void addStudent(Student student);

    Student findStudentById(int studentId);

    void updateStudent(Student student);

    void deleteStudentById(int studentId);

    List<Student> findAll();

    List<Course> findCoursesForStudent(Student student);
}
