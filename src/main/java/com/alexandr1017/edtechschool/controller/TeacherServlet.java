package com.alexandr1017.edtechschool.controller;

import com.alexandr1017.edtechschool.dto.TeacherDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.service.TeacherService;
import com.alexandr1017.edtechschool.service.impl.TeacherServiceImpl;
import com.alexandr1017.edtechschool.util.DtoFromJsonBuilder;
import com.alexandr1017.edtechschool.util.JSONBuilder;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/teachers/*")
public class TeacherServlet extends HttpServlet {

    private TeacherService teacherService;

    public TeacherServlet() {
        this.teacherService = TeacherServiceImpl.getInstance();
    }

    public TeacherServlet(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            Collection<TeacherDto> teacherDtos = teacherService.findAll();

            JSONBuilder.sendAsJson(resp, teacherDtos);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String teacherId = splits[1];
        TeacherDto teacherDto = teacherService.findTeacherById(Integer.parseInt(teacherId));

        if (teacherDto != null) {
            JSONBuilder.sendAsJson(resp, teacherDto);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            TeacherDto teacherDto = DtoFromJsonBuilder.createDtoFromJsonReq(req, TeacherDto.class);
            teacherService.addTeacher(teacherDto);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (JsonSyntaxException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            TeacherDto teacherDto = DtoFromJsonBuilder.createDtoFromJsonReq(req, TeacherDto.class);
            try {
                teacherService.updateTeacher(teacherDto);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (ItemNotFoundException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            }
        } catch (JsonSyntaxException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            teacherService.deleteTeacher(Integer.parseInt(courseId));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (ItemNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }


}
