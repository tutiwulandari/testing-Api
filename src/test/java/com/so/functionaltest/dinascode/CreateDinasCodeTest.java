package com.so.functionaltest.dinascode;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import com.so.util.Utility;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Slf4j
public class CreateDinasCodeTest {

    @Test(testName = "Station_P_003_CreateDinasCode_Success", description = "Verify Create Dinas Code Success")
    public void createDinasCodeSuccess() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_dinas_code_station_success.json");
        request.put("code", UUID.randomUUID().toString());
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),
                        ConstantParameter.PATH_PARAM_MODULE_DINAS_CODE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
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
    @Test(testName = "Station_N_007_CreateDinasCode_AuthorizationInvalid", description = "Verify Create Station when Authorization Invalid")
    public void createCodeStationWhenAuthInvalid() throws IOException, ParseException{
        JSONObject request = Utility.buildRequestJSON("request/station/create_dinas_code_station_success.json");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),ConstantParameter.PATH_PARAM_MODULE_DINAS_CODE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "ADMIN.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        Response response = given(requestSpecification)
                .log().all().post();
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
    @Test(testName = "Station_N_008_CreateDinasCode_TokenInvalid", description = "Verify When Token Invalid, part authorization blank")
    public void createStationTokenIsBlank() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_dinas_code_station_success.json");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),ConstantParameter.PATH_PARAM_MODULE_DINAS_CODE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
        Response response = given(requestSpecification)
                .log().all().post();
        System.out.println(response.getBody().prettyPrint());
         response
                .then()
                .statusCode(401)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract();
//        System.out.println(extract.response().getBody().prettyPrint());
    }
    @Test(testName = "Station_N_002_CreateStation_ReqBodyInvalid", description = "Verify Request Body Invalid")
    public void createStationRequestBodyInvalid() throws IOException, ParseException {
        JSONObject request = Utility.
                buildRequestJSON("request/station/create_dinas_code_station_success.json");
        request.remove("code");
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(), ConstantParameter.PATH_PARAM_MODULE_DINAS_CODE_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
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

    @AfterMethod
    public void clearData() {
        Utility.getClearData();
    }

}
