package com.so.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;

public class Utility {


    public static final JSONParser JSON_PARSER = new JSONParser();

    public static JSONObject buildRequestJSON(String path) throws IOException, ParseException {
        var pathFileRequest = path;
        var file = new ClassPathResource(pathFileRequest).getFile();
        JSONObject request = (JSONObject) JSON_PARSER.parse(new FileReader(file));
        return request;
    }
}
