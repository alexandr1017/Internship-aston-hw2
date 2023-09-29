package com.alexandr1017.edtechschool.dao;

import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;

import java.util.List;

public interface CourseDao {
    void addCourse(Course course);
    Course getCourseById(int courseId);
    List<Course> findAll();
    void updateCourse(Course course);
    void deleteCourseById(int courseId);

    List<Student> findStudentsOnCourse(Course course);
}
