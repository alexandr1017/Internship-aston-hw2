package com.alexandr1017.edtechschool.controller;

import com.alexandr1017.edtechschool.service.ManagementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import static org.mockito.Mockito.*;

class ManagementServletTest {

    private ManagementService managementService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        managementService = mock(ManagementService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    void doPost() throws IOException, ServletException {
        when(response.getWriter()).thenReturn(writer);

        String studentId = "1";
        String courseId = "1";

        when(request.getParameter("studentId")).thenReturn(studentId);
        when(request.getParameter("courseId")).thenReturn(courseId);

        ManagementServlet managementServlet = new ManagementServlet();
        managementServlet.setManagementService(managementService);
        managementServlet.doPost(request, response);

        verify(managementService).addStudentToCourse(Integer.parseInt(studentId), Integer.parseInt(courseId));
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doDelete() throws IOException {
        when(response.getWriter()).thenReturn(writer);

        String studentId = "1";
        String courseId = "1";

        when(request.getParameter("studentId")).thenReturn(studentId);
        when(request.getParameter("courseId")).thenReturn(courseId);

        ManagementServlet managementServlet = new ManagementServlet();
        managementServlet.setManagementService(managementService);
        managementServlet.doDelete(request, response);

        verify(managementService).removeStudentFromCourse(Integer.parseInt(studentId), Integer.parseInt(courseId));
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void doPut() throws IOException {
        when(response.getWriter()).thenReturn(writer);

        String teacherId = "1";
        String courseId = "1";
        String action = "add";

        when(request.getParameter("teacherId")).thenReturn(teacherId);
        when(request.getParameter("courseId")).thenReturn(courseId);
        when(request.getParameter("action")).thenReturn(action);

        ManagementServlet managementServlet = new ManagementServlet();
        managementServlet.setManagementService(managementService);
        managementServlet.doPut(request, response);

        if ("add".equals(action)) {
            verify(managementService).addTeacherToCourse(Integer.parseInt(teacherId), Integer.parseInt(courseId));
        } else if ("replace".equals(action)) {
            verify(managementService).replaceTeacherOnCourse(Integer.parseInt(teacherId), Integer.parseInt(courseId));
        }
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}