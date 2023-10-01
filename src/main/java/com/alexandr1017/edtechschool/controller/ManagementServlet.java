package com.alexandr1017.edtechschool.controller;

import com.alexandr1017.edtechschool.exception.ItemNotFoundException;
import com.alexandr1017.edtechschool.service.ManagementService;
import com.alexandr1017.edtechschool.service.impl.ManagementServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/management/*")
public class ManagementServlet extends HttpServlet {

    private ManagementService managementService = ManagementServiceImpl.getInstance();

    public void setManagementService(ManagementService managementService) {
        this.managementService = managementService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            int courseId = Integer.parseInt(req.getParameter("courseId"));

            managementService.addStudentToCourse(studentId, courseId);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid studentId or courseId");
        } catch (ItemNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {

            int studentId = Integer.parseInt(req.getParameter("studentId"));
            int courseId = Integer.parseInt(req.getParameter("courseId"));

            managementService.removeStudentFromCourse(studentId, courseId);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid studentId or courseId");
        } catch (ItemNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int teacherId = Integer.parseInt(req.getParameter("teacherId"));
            int courseId = Integer.parseInt(req.getParameter("courseId"));
            String action = req.getParameter("action");

            if ("add".equals(action)) {
                managementService.addTeacherToCourse(teacherId, courseId);
            } else if ("replace".equals(action)) {
                managementService.replaceTeacherOnCourse(teacherId, courseId);
            }

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid teacherId or courseId");
        } catch (ItemNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }


}
