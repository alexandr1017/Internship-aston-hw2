package com.alexandr1017.edtechschool.service;

import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;

public interface ManagementService {

    void addStudentToCourse(int studentId, int courseId);

    void removeStudentFromCourse(int studentId, int courseId);

    void addTeacherToCourse(int teacherId, int courseId);
    void replaceTeacherOnCourse(int teacherId, int courseId);

}
