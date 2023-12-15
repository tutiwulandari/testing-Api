package com.so.functionaltest.station;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
@Slf4j
public class ListStationTest {
    @Test(testName = "TC01", description = "Verify Get List Station Success")
    public void getListStation()  {
        RequestSpecification requestSpecification = createRequest();
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification).get().then().
                statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data", notNullValue())
                .extract();
                extract.response().prettyPrint();
//        System.out.println(extract.response().getBody().prettyPrint());

    }

    @Test
    public void getListStationTokenIsBlank()  {
        RequestSpecification requestSpecification = createRequest();
        ExtractableResponse<Response> extract = given(requestSpecification).get().then().
                statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data", notNullValue())
                .extract();
        extract.response().prettyPrint();
    }

    private RequestSpecification createRequest() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .setBaseUri(ConstantParameter.BASE_URI + ConstantParameter.PATH_PARAM_MODULE_GET_STATION);
        return requestSpecBuilder.build();
    }

}
