package com.so.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Utility {


    public static final JSONParser JSON_PARSER = new JSONParser();

    public static JSONObject buildRequestJSON(String path) throws IOException, ParseException {
        var pathFileRequest = path;
        var file = new ClassPathResource(pathFileRequest).getFile();
        JSONObject request = (JSONObject) JSON_PARSER.parse(new FileReader(file));
        return request;
    }

    public static RequestSpecification createRequest(String pathParam) {
        setBaseURI();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .setBaseUri(ConstantParameter.BASE_URI + pathParam);
        return requestSpecBuilder.build();
    }

    public static RequestSpecification createRequestWithHeader(String pathParam ,Map<String,String> addHeaders) {
        setBaseURI();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .addHeaders(addHeaders)
                .setBaseUri(ConstantParameter.BASE_URI + pathParam);
        return requestSpecBuilder.build();
    }

    public static RequestSpecification createRequestWithBodyAndHeaders(String body, String pathParam, Map<String,String> addHeaders) {
        setBaseURI();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .addHeaders(addHeaders)
                .setBaseUri(ConstantParameter.BASE_URI + pathParam)
                .setBody(body);
        return requestSpecBuilder.build();

    }

    private static void setBaseURI() {
        if(ConstantParameter.RUN_MODE == "MOCK") {
            ConstantParameter.BASE_URI = "https://mock.apidog.com/m1/416892-0-2a2c955f";
        } else if(ConstantParameter.RUN_MODE == "SIT") {
            ConstantParameter.BASE_URI = "https://mock.apidog.com/m1/416892-0-2a2c955f";
        }
    }

    public static void getClearData() {
        RequestSpecification requestSpecification = Utility.createRequest(ConstantParameter.PATH_PARAM_MODULE_CLEAR_DATA );
        ValidatableResponse response =  given(requestSpecification).get()
                .then().statusCode(200)
                .log().all();
        System.out.println(response.extract().asPrettyString());

    }
}
