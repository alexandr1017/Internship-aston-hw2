package com.alexandr1017.edtechschool.dao;


public interface ManagementDao {
    void addStudentToCourse(int studentId, int courseId);
    void removeStudentFromCourse(int studentId, int courseId);
    void addTeacherToCourse(int teacherId, int courseId);
    void replaceTeacherOnCourse(int teacherId, int courseId);

}
