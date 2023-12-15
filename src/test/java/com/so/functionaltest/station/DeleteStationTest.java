package com.so.functionaltest.station;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import com.so.util.Utility;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class DeleteStationTest {

    @Test(testName = "TC01", description = "Verify Delete Station Success")
    //
    public void deleteStation() throws IOException, ParseException  {
        JSONObject request = buildRequestJSON();
        RequestSpecification requestSpecification = createRequest(request.toJSONString());
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().delete("")
                .then()
                .statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());

    }

    private static JSONObject buildRequestJSON() throws IOException, ParseException {
        var pathFileRequest = "request/station/delete_station_success.json";
        var file = new ClassPathResource(pathFileRequest).getFile();
        JSONObject request = (JSONObject) Utility.JSON_PARSER.parse(new FileReader(file));
        return request;
    }
    private RequestSpecification createRequest(String body) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .setBaseUri(ConstantParameter.BASE_URI + ConstantParameter.PATH_PARAM_MODULE_STATION)
                .setBody(body);
        return requestSpecBuilder.build();
    }

}
