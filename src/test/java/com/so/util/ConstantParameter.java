package com.so.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantParameter {


    public static final String BASE_URI = "http://0.0.0.0:8081";

    public static final String PATH_PARAM_MODULE_STATION = "/v2/station";


    public static final Map<String, String> HTTP_HEADER_TEMPLATE = new HashMap<>();

    static {
        HTTP_HEADER_TEMPLATE.put("User-Agent", "Testing-Integration");
        HTTP_HEADER_TEMPLATE.put("Content-Type", "application/json");
        HTTP_HEADER_TEMPLATE.put("Accept-Encoding", "gzip, deflate, br");
    }
}