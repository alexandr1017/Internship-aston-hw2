package com.alexandr1017.edtechschool.dto;


import com.alexandr1017.edtechschool.model.Course;


import java.util.List;
import java.util.Objects;


public class CourseDto {

    private int id;
    private String name;
    private int price;
    private int duration;
    private List<StudentDto> studentDtos;

    public CourseDto(int id, String name, int duration, int price, List<StudentDto> studentDtos) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.studentDtos = studentDtos;
    }

    public CourseDto() {
    }

    public static CourseDto toDto(Course entity) {
        CourseDto dto = new CourseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDuration(entity.getDuration());
        dto.setPrice(entity.getPrice());
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<StudentDto> getStudentDtos() {
        return studentDtos;
    }

    public void setStudentDtos(List<StudentDto> studentDtos) {
        this.studentDtos = studentDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDto = (CourseDto) o;
        return id == courseDto.id && price == courseDto.price && duration == courseDto.duration && Objects.equals(name, courseDto.name) && Objects.equals(studentDtos, courseDto.studentDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, duration, studentDtos);
    }

    @Override
    public String toString() {
        return "CourseDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", duration=" + duration +
               ", studentDtos=" + studentDtos +
               '}';
    }
}
