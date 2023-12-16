package com.so.functionaltest.dinascode;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import com.so.util.Utility;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;


import java.io.IOException;

@Slf4j
public class CreateDinasCodeTest {

    @Test(testName = "TC01", description = "Verify Create Dinas Code Success")
    public void createDinasCodeSuccess() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_dinas_code_station_success.json");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBody(request.toJSONString(), ConstantParameter.PATH_PARAM_MODULE_DINAS_CODE_STATION);
        String token = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer "+ token);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post().then()
                .statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("timestamp", notNullValue())
                .body("success", equalTo(true))
                .body("message", notNullValue())
                .body("data", notNullValue())
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());

    }

}
