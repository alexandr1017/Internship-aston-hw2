package com.alexandr1017.edtechschool.dao.impl;

import com.alexandr1017.edtechschool.dao.StudentDao;
import com.alexandr1017.edtechschool.util.utilDb.DataSource;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;


public class StudentDaoImpl implements StudentDao {
    private HikariDataSource dataSource;

    public StudentDaoImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static StudentDaoImpl getInstance() {
        return new StudentDaoImpl(DataSource.getDataSource());
    }


    public void addStudent(Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO student (name, age, registration_date) VALUES (?, ?, ?)")
        ) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setDate(3, java.sql.Date.valueOf(student.getRegistrationDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student findStudentById(int studentId) {
        Student student = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM student WHERE id = ?")
        ) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public void updateStudent(Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement
                     stmt = connection.prepareStatement("UPDATE student SET name = ?, age = ?, registration_date = ? WHERE id = ?")
        ) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setDate(3, java.sql.Date.valueOf(student.getRegistrationDate()));
            stmt.setInt(4, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentById(int studentId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt1 = connection.prepareStatement("DELETE FROM course_student WHERE student_id = ?");
             PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM student WHERE id = ?")
        ) {
            stmt1.setInt(1, studentId);
            stmt1.executeUpdate();

            stmt2.setInt(1, studentId);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Student> findAll() {
        ArrayList<Student> students = new ArrayList<>();
        Student student;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM student")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;

    }

    public List<Course> findCoursesForStudent(Student student) {
        List<Course> courses = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement
                        stmt = connection.prepareStatement("SELECT c.id, c.name, c.duration, c.price, c.creating_date FROM course c JOIN course_student cs ON c.id = cs.course_id WHERE cs.student_id = ?")
        ) {
            stmt.setInt(1, student.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setPrice(rs.getInt("price"));
                course.setCreatingDate(rs.getDate("creating_date").toLocalDate());
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }


}
