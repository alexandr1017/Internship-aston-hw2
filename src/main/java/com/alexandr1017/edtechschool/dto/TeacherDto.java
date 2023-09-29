package com.alexandr1017.edtechschool.dto;


import com.alexandr1017.edtechschool.model.Teacher;

import java.util.List;
import java.util.Objects;

public class TeacherDto {


    private int id;
    private String name;
    private int age;
    private List<CourseDto> courses;

    public TeacherDto() {
    }

    public TeacherDto(int id, String name, int age, List<CourseDto> courses) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courses = courses;
    }

    public static TeacherDto toDto(Teacher entity) {
        TeacherDto dto = new TeacherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        return dto;
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

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDto that = (TeacherDto) o;
        return id == that.id && age == that.age && Objects.equals(name, that.name) && Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, courses);
    }


}
