package com.so.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantParameter {


    public static final String BASE_URI = "https://mock.apidog.com/m1/416892-0-2a2c955f";
    public static final String PATH_PARAM_MODULE_STATION = "/v2/stasiun";

    public static final String PATH_PARAM_MODULE_GET_STATION = "/v2/stasiun/list";

    public static final Map<String, String> HTTP_HEADER_TEMPLATE = new HashMap<>();

    static {
        HTTP_HEADER_TEMPLATE.put("User-Agent", "Apidog/1.0.0 (https://apidog.com)");
        HTTP_HEADER_TEMPLATE.put("Content-Type", "application/json");
        HTTP_HEADER_TEMPLATE.put("Accept-Encoding", "gzip, deflate, br");
        HTTP_HEADER_TEMPLATE.put("Accept", "*/*");
        HTTP_HEADER_TEMPLATE.put("Host", "mock.apidog.com");
        HTTP_HEADER_TEMPLATE.put("Cache-Control", "no-cache");
        HTTP_HEADER_TEMPLATE.put("Connection", "keep-alive");
    }
}
