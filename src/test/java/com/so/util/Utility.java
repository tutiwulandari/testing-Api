package com.so.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;

public class Utility {


    private static final JSONParser JSON_PARSER = new JSONParser();

    public static JSONObject buildRequestJSON(String pathFile) {
        try {
            File file = new ClassPathResource(pathFile).getFile();
            return (JSONObject) JSON_PARSER.parse(new FileReader(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
