package com.alexandr1017.edtechschool.controller;

import com.alexandr1017.edtechschool.dto.TeacherDto;
import com.alexandr1017.edtechschool.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServletTest {

    private TeacherService teacherService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        teacherService = mock(TeacherService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    void doGet() throws IOException {

        when(response.getWriter()).thenReturn(writer);

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1);
        teacherDto.setName("Test Teacher");
        List<TeacherDto> teacherDtos = Arrays.asList(teacherDto);

        when(teacherService.findAll()).thenReturn(teacherDtos);

        TeacherServlet teacherServlet = new TeacherServlet();
        teacherServlet.setTeacherService(teacherService);
        teacherServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding(StandardCharsets.UTF_8.name());
        String result = stringWriter.toString();
        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("\"name\":\"Test Teacher\""));
    }

    @Test
    void doGet_withTeacherId() throws IOException {
        when(response.getWriter()).thenReturn(writer);

        String teacherId = "1";
        when(request.getPathInfo()).thenReturn("/" + teacherId);

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1);
        teacherDto.setName("Test Teacher");

        when(teacherService.findTeacherById(Integer.parseInt(teacherId))).thenReturn(teacherDto);

        TeacherServlet teacherServlet = new TeacherServlet();
        teacherServlet.setTeacherService(teacherService);
        teacherServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding(StandardCharsets.UTF_8.name());
        String result = stringWriter.toString();
        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("\"name\":\"Test Teacher\""));
    }


    @Test
    void doPost() throws IOException {
        when(response.getWriter()).thenReturn(writer);

        String jsonRequest = "{\"id\":1,\"name\":\"Test Teacher\"}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1);
        teacherDto.setName("Test Teacher");


        TeacherServlet teacherServlet = new TeacherServlet();
        teacherServlet.setTeacherService(teacherService);
        teacherServlet.doPost(request, response);

        verify(teacherService).addTeacher(teacherDto);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPut() throws IOException {

        when(response.getWriter()).thenReturn(writer);

        String jsonRequest = "{\"id\":1,\"name\":\"Test Teacher\"}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1);
        teacherDto.setName("Test Teacher");


        TeacherServlet teacherServlet = new TeacherServlet();
        teacherServlet.setTeacherService(teacherService);
        teacherServlet.doPut(request, response);

        verify(teacherService).updateTeacher(teacherDto);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doDelete() throws IOException {

        when(response.getWriter()).thenReturn(writer);

        String pathInfo = "/1";
        when(request.getPathInfo()).thenReturn(pathInfo);

        TeacherServlet teacherServlet = new TeacherServlet();
        teacherServlet.setTeacherService(teacherService);
        teacherServlet.doDelete(request, response);

        verify(teacherService).deleteTeacher(Integer.parseInt(pathInfo.substring(1)));
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}