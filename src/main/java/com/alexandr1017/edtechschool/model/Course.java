package com.alexandr1017.edtechschool.model;

import java.time.LocalDate;

import java.util.List;

import java.util.Objects;

public class Course {


    private int id;
    private String name;
    private int duration;
    private int price;
    private LocalDate creatingDate;

    private int teacherId;
    private List<Student> students;



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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(LocalDate creatingDate) {
        this.creatingDate = creatingDate;
    }
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && duration == course.duration && price == course.price && Objects.equals(name, course.name) && Objects.equals(creatingDate, course.creatingDate) && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, price, creatingDate, students);
    }

    @Override
    public String toString() {
        return "Course{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", duration=" + duration +
               ", price=" + price +
               ", creatingDate=" + creatingDate +
               ", students=" + students +
               '}';
    }
}
