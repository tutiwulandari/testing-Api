package com.so.functionaltest.station;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import com.so.util.Utility;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class CreateStationTest {

    @Test(testName = "Station_P_001_CreateStation_Success", description = "Verify Create Station Success")
    public static void createStationSuccess() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_station_success.json");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),ConstantParameter.PATH_PARAM_MODULE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "ADMIN.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post()
                .then()
                .statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data.uuid", notNullValue())
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());

    }

    @Test(testName = "Station_N_003_CreateStation_when_CodeStationExist",
            description = "Verify Create Station when Code Station is Exist")
    public void createStationWhenCodeStationExist() throws IOException, ParseException{
        createStationSuccess();
        JSONObject request = Utility.buildRequestJSON("request/station/create_station_success.json");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),ConstantParameter.PATH_PARAM_MODULE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "ADMIN.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post()
                .then()
                .statusCode(400)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());

    }
    @Test(testName = "Station_N_001_CreateStation_TokenInvalid",
            description = "Verify When Token Invalid, part authorization blank")
    public void createStationTokenIsBlank() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_station_success.json");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),ConstantParameter.PATH_PARAM_MODULE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "ADMIN.admin"));
        given(requestSpecification)
                .log().all().post()
                .then()
                .statusCode(401)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract().response().prettyPrint();
    }


    @Test(testName = "Station_N_002_CreateStation_ReqBodyInvalid", description = "Verify Request Body Invalid")
    public void createStationRequestBodyInvalid() throws IOException, ParseException {
        JSONObject request = Utility.
                buildRequestJSON("request/station/create_station_success.json");
        request.remove("name");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),ConstantParameter.PATH_PARAM_MODULE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "ADMIN.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post()
                .then()
                .statusCode(400)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("error", notNullValue())
                .extract();
        extract.response().prettyPrint();
        System.out.println(extract.response().getBody().prettyPrint());
    }

    @Test(testName = "Station_N004_CreateStation_AuthorizationInvalid", description = "Verify Create Station when Authorization Invalid")
    public void createStationWhenAuthInvalid() throws IOException, ParseException{
        JSONObject request = Utility.buildRequestJSON("request/station/create_station_success.json");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),ConstantParameter.PATH_PARAM_MODULE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "CIVIL_WORK.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        Response response = given(requestSpecification)
                .log().all().post();
        System.out.println(response.getHeader("TRACE-ID"));
        System.out.println(response.getBody().prettyPrint());
        response
                .then()
                .statusCode(403)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract();

    }

    @AfterMethod
    public void clearData() {
        Utility.getClearData();

    }
}
