package com.alexandr1017.edtechschool.service.impl;

import com.alexandr1017.edtechschool.dao.*;
import com.alexandr1017.edtechschool.dao.impl.CourseDaoImpl;
import com.alexandr1017.edtechschool.dao.impl.ManagementDaoImpl;
import com.alexandr1017.edtechschool.dao.impl.StudentDaoImpl;
import com.alexandr1017.edtechschool.dao.impl.TeacherDaoImpl;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;
import com.alexandr1017.edtechschool.model.Teacher;
import com.alexandr1017.edtechschool.service.ManagementService;

public class ManagementServiceImpl implements ManagementService {


    private static final ManagementService INSTANCE = new ManagementServiceImpl();

    private ManagementDao managementDao = ManagementDaoImpl.getInstance();
    private final CourseDao courseDao = CourseDaoImpl.getInstance();
    private final StudentDao studentDao = StudentDaoImpl.getInstance();
    private final TeacherDao teacherDao = TeacherDaoImpl.getInstance();

    private ManagementServiceImpl() {
    }

    private ManagementServiceImpl(ManagementDao managementDao) {
        this.managementDao = managementDao;
    }


    @Override
    public void addStudentToCourse(int studentId, int courseId) {

        searchedOrThrowExc(studentId, courseId, "student");

        managementDao.addStudentToCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {

        searchedOrThrowExc(studentId, courseId, "student");

        managementDao.removeStudentFromCourse(studentId, courseId);

    }

    @Override
    public void addTeacherToCourse(int teacherId, int courseId) {
        searchedOrThrowExc(teacherId, courseId, "teacher");

        managementDao.addTeacherToCourse(teacherId, courseId);
    }

    @Override
    public void replaceTeacherOnCourse(int teacherId, int courseId) {
        searchedOrThrowExc(teacherId, courseId, "teacher");

        managementDao.replaceTeacherOnCourse(teacherId, courseId);
    }

    private void searchedOrThrowExc(int id, int courseId, String type) {
        Course courseById = courseDao.getCourseById(courseId);
        if (courseById == null) {
            throw new ItemNotFoundException("Курс с таким Id: " + courseId + " не найден");
        }

        if (type.equals("student")) {
            Student studentById = studentDao.findStudentById(id);
            if (studentById == null) {
                throw new ItemNotFoundException("Студент с таким Id: " + id + " не найден");
            }
        } else if (type.equals("teacher")) {
            Teacher teacherById = teacherDao.findTeacherById(id);
            if (teacherById == null) {
                throw new ItemNotFoundException("Учитель с таким Id: " + id + " не найден");
            }
        }
    }



    public static ManagementService getInstance() {
        return INSTANCE;
    }

    public static ManagementService getInstance(ManagementDao managementDao) {
        return new ManagementServiceImpl(managementDao);
    }
}
