package com.so.invokesapi;

import com.so.util.ConstantParameter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.given;

public class LoginAPI {


    public static String getTokenWhenUsingLogin() {
        JSONObject request = new JSONObject();
        request.put("username", "demo-ta");
        request.put("password", "75673aad502f3dd315172de4f34d58cc");
        request.put("deviceType", "WEB");
        request.put("deviceId", "d2131dewfe@!$@#");


        var validatableResponse = invokeLogin(request);
        ResponseBodyExtractionOptions body = validatableResponse.extract().body();
        JsonPath jsonPath = body.jsonPath();
        return jsonPath.get("token");
    }

    public static ValidatableResponse invokeLogin(JSONObject request) {
        ValidatableResponse validatableResponse;

        validatableResponse = given(createRequest(request.toJSONString())).relaxedHTTPSValidation().log().all()
                .post().then().statusCode(200);
        return validatableResponse;
    }


    private static RequestSpecification createRequest(String body) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .setBaseUri("https://dev.lrtosis.com:30000/v2/login")
                .setBody(body);
        return requestSpecBuilder.build();
    }
}
