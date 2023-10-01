package com.alexandr1017.edtechschool.controller;

import com.alexandr1017.edtechschool.dto.CourseDto;
import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.service.CourseService;
import com.alexandr1017.edtechschool.service.impl.CourseServiceImpl;
import com.alexandr1017.edtechschool.util.DtoFromJsonBuilder;
import com.alexandr1017.edtechschool.util.JSONBuilder;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Collection;


@WebServlet("/courses/*")
public class CourseServlet extends HttpServlet {

    private CourseService courseService = CourseServiceImpl.getInstance();


    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {

            Collection<CourseDto> courseDtos = courseService.findAll();

            JSONBuilder.sendAsJson(resp, courseDtos);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String courseId = splits[1];
        CourseDto courseDto = courseService.getCourseById(Integer.parseInt(courseId));

        if (courseDto != null) {
            JSONBuilder.sendAsJson(resp, courseDto);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            CourseDto courseDto = DtoFromJsonBuilder.createDtoFromJsonReq(req, CourseDto.class);
            courseService.addCourse(courseDto);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (JsonSyntaxException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            CourseDto courseDto = DtoFromJsonBuilder.createDtoFromJsonReq(req, CourseDto.class);

            try {
                courseService.updateCourse(courseDto);
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
            courseService.deleteCourseById(Integer.parseInt(courseId));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (ItemNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

}
