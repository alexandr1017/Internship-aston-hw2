package com.alexandr1017.edtechschool.dao.impl;

import com.alexandr1017.edtechschool.dao.CourseDao;
import com.alexandr1017.edtechschool.util.utilDb.DataSource;
import com.alexandr1017.edtechschool.model.Course;
import com.alexandr1017.edtechschool.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    private final Connection connection;


    public CourseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public static CourseDaoImpl getInstance() {
        return new CourseDaoImpl(DataSource.getConnection());
    }

    public void addCourse(Course course) {
        try (
                PreparedStatement stmt
                        = connection.prepareStatement("INSERT INTO course (name, duration, price, creating_date) VALUES (?, ?, ?,?)")
        ) {
            stmt.setString(1, course.getName());
            stmt.setInt(2, course.getDuration());
            stmt.setInt(3, course.getPrice());
            stmt.setDate(4, java.sql.Date.valueOf(course.getCreatingDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Course getCourseById(int courseId) {
        Course course = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM course WHERE id = ?")
        ) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setPrice(rs.getInt("price"));
                course.setCreatingDate(rs.getDate("creating_date").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    public List<Course> findAll() {
        ArrayList<Course> courses = new ArrayList<>();
        Course course;
        try (
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM course")
        ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                course = new Course();
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

    public void updateCourse(Course course) {
        try (PreparedStatement stmt
                     = connection.prepareStatement("UPDATE course SET name = ?, duration = ?, price = ?, creating_date = ? WHERE id = ?")) {

            stmt.setString(1, course.getName());
            stmt.setInt(2, course.getDuration());
            stmt.setInt(3, course.getPrice());
            stmt.setDate(4, java.sql.Date.valueOf(course.getCreatingDate()));
            stmt.setInt(5, course.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourseById(int courseId) {
        try (
                PreparedStatement stmt1 = connection.prepareStatement("DELETE FROM course_student WHERE course_id = ?");
                PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM course WHERE id = ?")
        ) {
            stmt1.setInt(1, courseId);
            stmt1.executeUpdate();

            stmt2.setInt(1, courseId);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Student> findStudentsOnCourse(Course course) {
        List<Student> students = new ArrayList<>();
        try (
                PreparedStatement stmt
                        = connection.prepareStatement("SELECT s.id, s.name, s.age, s.registration_date FROM student s JOIN course_student cs ON s.id = cs.student_id WHERE cs.course_id = ?")
        ) {
            stmt.setInt(1, course.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
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
}

