package com.alexandr1017.edtechschool.controller;

import com.alexandr1017.edtechschool.dto.StudentDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;

import com.alexandr1017.edtechschool.service.StudentService;

import com.alexandr1017.edtechschool.service.impl.StudentServiceImpl;
import com.alexandr1017.edtechschool.util.DtoFromJsonBuilder;
import com.alexandr1017.edtechschool.util.JSONBuilder;

import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.Collection;

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {
    private StudentService studentService;

    public StudentServlet() {
        this.studentService = StudentServiceImpl.getInstance();
    }

    public StudentServlet(StudentService studentService) {
        this.studentService = studentService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            Collection<StudentDto> studentDtos = studentService.findAll();

            JSONBuilder.sendAsJson(resp, studentDtos);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String studentId = splits[1];
        StudentDto studentDto = studentService.getStudentById(Integer.parseInt(studentId));

        if (studentDto != null) {
            JSONBuilder.sendAsJson(resp, studentDto);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            StudentDto studentDto = DtoFromJsonBuilder.createDtoFromJsonReq(req, StudentDto.class);
            studentService.addStudent(studentDto);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (JsonSyntaxException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            StudentDto studentDto = DtoFromJsonBuilder.createDtoFromJsonReq(req, StudentDto.class);
            try {
                studentService.updateStudent(studentDto);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (ItemNotFoundException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            }
        } catch (JsonSyntaxException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String courseId = splits[1];

        try {
            studentService.deleteStudentById(Integer.parseInt(courseId));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (ItemNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

}
