package com.alexandr1017.edtechschool.dao.impl;

import com.alexandr1017.edtechschool.dao.ManagementDao;
import com.alexandr1017.edtechschool.util.utilDb.DataSource;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManagementDaoImpl implements ManagementDao {


    private HikariDataSource dataSource;

    public ManagementDaoImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ManagementDaoImpl getInstance() {
        return new ManagementDaoImpl(DataSource.getDataSource());
    }

    public void addStudentToCourse(int studentId, int courseId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement
                     stmt = connection.prepareStatement("INSERT INTO course_student (course_id, student_id) VALUES (?, ?)")
        ) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStudentFromCourse(int studentId, int courseId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement
                     stmt = connection.prepareStatement("DELETE FROM course_student WHERE course_id = ? AND student_id = ?")
        ) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addTeacherToCourse(int teacherId, int courseId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("UPDATE course SET teacher_id = ? WHERE id = ?");) {
            stmt.setInt(1, teacherId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void replaceTeacherOnCourse(int teacherId, int courseId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("UPDATE course SET teacher_id = ? WHERE id = ?");) {
            stmt.setInt(1, teacherId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
