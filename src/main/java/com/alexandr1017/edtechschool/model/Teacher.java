package com.alexandr1017.edtechschool.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Teacher {


    private int id;
    private String name;
    private int age;
    private LocalDate hireDate;

    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

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

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && age == teacher.age && Objects.equals(name, teacher.name) && Objects.equals(hireDate, teacher.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, hireDate);
    }


}

