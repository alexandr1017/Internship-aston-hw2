package com.alexandr1017.edtechschool.dto;

import com.alexandr1017.edtechschool.model.Student;

import java.util.List;
import java.util.Objects;


public class StudentDto {


    private int id;
    private String name;
    private int age;


    private List<CourseDto> courseDtos;

    public StudentDto() {
    }


    public StudentDto(int id, String name, int age, List<CourseDto> courseDtos) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courseDtos = courseDtos;
    }


    public static StudentDto toDto(Student entity) {
        StudentDto dto = new StudentDto();
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

    public List<CourseDto> getCourseDtos() {
        return courseDtos;
    }

    public void setCourseDtos(List<CourseDto> courseDtos) {
        this.courseDtos = courseDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return id == that.id && age == that.age && Objects.equals(name, that.name) && Objects.equals(courseDtos, that.courseDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, courseDtos);
    }

    @Override
    public String toString() {
        return "StudentDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", age=" + age +
               ", courseDtos=" + courseDtos +
               '}';
    }
}
