package com.so.functionaltest.masterstation;

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
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class CreateMasterStationTest {

    @Test(testName = "TC01", description = "Verify Create Master Station Success")
    public void createMasterStationSuccess() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_master_station_success.json");
        String usernameMasterStation = ListMasterStationTest.getUsernameMasterStation();
        RequestSpecification requestSpecification = createRequest(request.toJSONString(), usernameMasterStation);
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        System.out.println("user " + usernameMasterStation);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post()
                .then()
                .statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data.totalRowInserted", equalTo(2))
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());

    }

    @Test(testName= "TC02")

    private RequestSpecification createRequest(String body, String path) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeaders(ConstantParameter.HTTP_HEADER_TEMPLATE)
                .setBaseUri(ConstantParameter.BASE_URI + ConstantParameter.PATH_PARAM_MODULE_MASTER_STATION + "/" + path )
                .setBody(body);
        return requestSpecBuilder.build();
    }

}
