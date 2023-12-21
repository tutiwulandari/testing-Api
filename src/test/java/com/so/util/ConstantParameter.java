package com.so.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantParameter {
    public static String BASE_URI = "https://9ef4-118-99-110-0.ngrok-free.app";
    public static final String PATH_PARAM_MODULE_STATION = "/v2/stasiun";

    public static final String PATH_PARAM_MODULE_GET_STATION = "/v2/stasiun/list";

    public static final Map<String, String> HTTP_HEADER_TEMPLATE = new HashMap<>();

    public static final String PATH_PARAM_MODULE_MASTER_STATION = "/v2/kepalaStasiun/nipp";

    public static final String PATH_PARAM_MODULE_GET_MASTER_STATION = "/v2/kepalaStasiun/list";

    public static final String PATH_PARAM_MODULE_GET_DINAS_CODE_STATION = "/v2/so/kodeDinas/list";
    public static final String PATH_PARAM_MODULE_DINAS_CODE_STATION = "/v2/so/kodeDinas";

    public static final String RUN_MODE = "LOCAL";

    public static final String PATH_PARAM_MODULE_CLEAR_DATA = "/v2/stasiun/clear";



    static {
        HTTP_HEADER_TEMPLATE.put("User-Agent", "Apidog/1.0.0 (https://apidog.com)");
        HTTP_HEADER_TEMPLATE.put("Content-Type", "application/json");
        HTTP_HEADER_TEMPLATE.put("Accept-Encoding", "gzip, deflate, br");
        HTTP_HEADER_TEMPLATE.put("Accept", "*/*");
//        HTTP_HEADER_TEMPLATE.put("Host", "mock.apidog.com");
        HTTP_HEADER_TEMPLATE.put("Cache-Control", "no-cache");
        HTTP_HEADER_TEMPLATE.put("Connection", "keep-alive");
    }
}
