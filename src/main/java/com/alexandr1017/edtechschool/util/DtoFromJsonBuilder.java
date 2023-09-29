package com.alexandr1017.edtechschool.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class DtoFromJsonBuilder {

    private DtoFromJsonBuilder(){}
    public static <T> T createDtoFromJsonReq(HttpServletRequest req, Class<T> clazz) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String payload = sb.toString();

        // Parse the JSON payload into a Course object
        Gson gson = new Gson();
        return gson.fromJson(payload, clazz);
    }
}
