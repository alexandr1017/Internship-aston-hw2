package com.alexandr1017.edtechschool.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Student {

    private int id;
    private String name;
    private int age;
    private LocalDate registrationDate;
    private List<Course> courses;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && age == student.age && Objects.equals(name, student.name) && Objects.equals(registrationDate, student.registrationDate) && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, registrationDate, courses);
    }

    @Override
    public String toString() {
        return "Student{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", age=" + age +
               ", registrationDate=" + registrationDate +
               ", courses=" + courses +
               '}';
    }
}
