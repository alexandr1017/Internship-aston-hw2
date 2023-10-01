package com.alexandr1017.edtechschool.controller;

import com.alexandr1017.edtechschool.dto.StudentDto;
import com.alexandr1017.edtechschool.service.StudentService;
import jakarta.servlet.ServletException;
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

class StudentServletTest {

    private StudentService studentService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        studentService = mock(StudentService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    void doGet() throws IOException {

        when(response.getWriter()).thenReturn(writer);

        StudentDto studentDto = new StudentDto();
        studentDto.setId(1);
        studentDto.setName("Test Student");
        List<StudentDto> studentDtos = Arrays.asList(studentDto);

        when(studentService.findAll()).thenReturn(studentDtos);

        StudentServlet studentServlet = new StudentServlet();
        studentServlet.setStudentService(studentService);
        studentServlet.doGet(request, response);


        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding(StandardCharsets.UTF_8.name());
        String result = stringWriter.toString();
        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("\"name\":\"Test Student\""));
    }

    @Test
    void doGet_withStudentId() throws IOException {
        // Подготовка данных
        when(response.getWriter()).thenReturn(writer);

        String studentId = "1";
        when(request.getPathInfo()).thenReturn("/" + studentId);

        StudentDto studentDto = new StudentDto();
        studentDto.setId(1);
        studentDto.setName("Test Student");

        when(studentService.getStudentById(Integer.parseInt(studentId))).thenReturn(studentDto);

        StudentServlet studentServlet = new StudentServlet();
        studentServlet.setStudentService(studentService);
        studentServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding(StandardCharsets.UTF_8.name());
        String result = stringWriter.toString();
        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("\"name\":\"Test Student\""));
    }

    @Test
    void doPost() throws IOException {

        when(response.getWriter()).thenReturn(writer);

        String jsonRequest = "{\"id\":1,\"name\":\"Test Student\"}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        StudentDto studentDto = new StudentDto();
        studentDto.setId(1);
        studentDto.setName("Test Student");


        StudentServlet studentServlet = new StudentServlet();
        studentServlet.setStudentService(studentService);
        studentServlet.doPost(request, response);


        verify(studentService).addStudent(studentDto);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPut() throws IOException {
        when(response.getWriter()).thenReturn(writer);

        String jsonRequest = "{\"id\":1,\"name\":\"Test Student\"}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        StudentDto studentDto = new StudentDto();
        studentDto.setId(1);
        studentDto.setName("Test Student");


        StudentServlet studentServlet = new StudentServlet();
        studentServlet.setStudentService(studentService);
        studentServlet.doPut(request, response);

        verify(studentService).updateStudent(studentDto);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doDelete() throws IOException, ServletException {

        when(response.getWriter()).thenReturn(writer);

        String pathInfo = "/1";
        when(request.getPathInfo()).thenReturn(pathInfo);

        StudentServlet studentServlet = new StudentServlet();
        studentServlet.setStudentService(studentService);
        studentServlet.doDelete(request, response);

        verify(studentService).deleteStudentById(Integer.parseInt(pathInfo.substring(1)));
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}