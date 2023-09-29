package com.alexandr1017.edtechschool.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class JSONBuilder {
   private static final Gson GSON = new Gson();

   private JSONBuilder(){}

    public static void sendAsJson(
            HttpServletResponse response,
            Object obj) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String res = GSON.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }
}
