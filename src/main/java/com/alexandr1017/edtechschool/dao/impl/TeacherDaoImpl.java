package com.alexandr1017.edtechschool.dao.impl;


import com.alexandr1017.edtechschool.dao.TeacherDao;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Teacher;
import com.alexandr1017.edtechschool.util.utilDb.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {
    private Connection connection;

    public TeacherDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public static TeacherDaoImpl getInstance() {
        return new TeacherDaoImpl(DataSource.getConnection());
    }


    // CREATE
    @Override
    public void addTeacher(Teacher teacher) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO teacher (name, age, hire_date) VALUES (?, ?, ?)");) {
            stmt.setString(1, teacher.getName());
            stmt.setInt(2, teacher.getAge());
            stmt.setDate(3, java.sql.Date.valueOf(teacher.getHireDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    // READ
    @Override
    public Teacher findTeacherById(int teacherId) {
        Teacher teacher = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?");) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teacher.setAge(rs.getInt("age"));
                teacher.setHireDate(rs.getDate("hire_date").toLocalDate());
                return teacher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public List<Teacher> findAll() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        Teacher teacher;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM teacher")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teacher.setAge(rs.getInt("age"));
                teacher.setHireDate(rs.getDate("hire_date").toLocalDate());
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;

    }

    // UPDATE
    @Override
    public void updateTeacher(Teacher teacher) {

        try (PreparedStatement stmt = connection.prepareStatement("UPDATE teacher SET name = ?, age = ?, hire_date = ? WHERE id = ?");
        ) {
            stmt.setString(1, teacher.getName());
            stmt.setInt(2, teacher.getAge());
            stmt.setDate(3, java.sql.Date.valueOf(teacher.getHireDate()));
            stmt.setInt(4, teacher.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    @Override
    public void deleteTeacher(int teacherId) {

        try (PreparedStatement updateStmt = connection.prepareStatement("UPDATE course SET teacher_id = NULL WHERE teacher_id = ?");
             PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM teacher WHERE id = ?")
        ) {
            updateStmt.setInt(1, teacherId);
            updateStmt.executeUpdate();
            deleteStmt.setInt(1, teacherId);
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public List<Course> findCoursesForTeacher(Teacher teacher) {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM course WHERE teacher_id = ?")) {
            stmt.setInt(1, teacher.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setPrice(rs.getInt("price"));
                course.setCreatingDate(rs.getDate("creating_date").toLocalDate());
                course.setTeacherId(rs.getInt("teacher_id"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }


}


