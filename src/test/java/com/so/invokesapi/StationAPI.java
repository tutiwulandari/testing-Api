package com.so.invokesapi;

import com.so.util.ConstantParameter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class StationAPI {

    public static ValidatableResponse invokeCreateStation(Map<String, String> headers, JSONObject body) {

        var requestSpecification = new RequestSpecBuilder().addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .setBaseUri(ConstantParameter.BASE_URI + ConstantParameter.PATH_PARAM_MODULE_STATION)
                .setBody(body.toJSONString()).build();
        if (!CollectionUtils.isEmpty(headers)) {
            requestSpecification.headers(headers);
        }
        var tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        var validatableResponse = given(requestSpecification)
                .log().all().post()
                .then();
        System.out.println("result body: " + validatableResponse.extract().response().prettyPrint());
        return validatableResponse;
    }

    public static ValidatableResponse invokeUpdateStation(Map<String, String> headers, JSONObject body, String path) {

        var requestSpecification = new RequestSpecBuilder()
                .addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .setBaseUri(ConstantParameter.BASE_URI + ConstantParameter.PATH_PARAM_MODULE_STATION + "/" + path)
                .setBody(body.toJSONString()).build();
        if (!CollectionUtils.isEmpty(headers)) {
            requestSpecification.headers(headers);
        }
        var tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        var validatableResponse = given(requestSpecification)
                .log().all().patch()
                .then();
        System.out.println("result body: " + validatableResponse.extract().response().prettyPrint());
        return validatableResponse;
    }

}