package com.so.functionaltest.masterstation;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import com.so.util.Utility;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Slf4j
public class CreateMasterStationTest {

    @Test(testName = "Station_P_004_CreateMasterStasion_Success", description = "Verify Create Master Station Success")
    public void createMasterStationSuccess() throws IOException, ParseException {

        JSONObject request = Utility.buildRequestJSON("request/station/create_master_station_success.json");
        String usernameMasterStation = ListMasterStationTest.getUsernameMasterStation();
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(), ConstantParameter.PATH_PARAM_MODULE_MASTER_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        System.out.println("wulan " + usernameMasterStation);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post("/" + usernameMasterStation)
                .then()
                .statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data.totalRowInserted", equalTo(0))
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());

    }

    @Test(testName = "Station_N_011_CreateMasterStation_TokenInvalid")
    public void createMasterStationTokenIsBlank() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_master_station_success.json");
        String usernameMasterStation = ListMasterStationTest.getUsernameMasterStation();
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(), ConstantParameter.PATH_PARAM_MODULE_MASTER_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post("/" + usernameMasterStation)
                .then()
                .statusCode(401)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data.totalRowInserted", nullValue())
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());
    }

    @Test(testName = "Station_N_012_CreateMasterStation_AuthInvalid")
    public void createMasterStationAuthInvalid() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_master_station_success.json");
        String usernameMasterStation = ListMasterStationTest.getUsernameMasterStation();
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(), ConstantParameter.PATH_PARAM_MODULE_MASTER_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "ADMIN.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        System.out.println("wulan " + usernameMasterStation);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post("/" + usernameMasterStation)
                .then()
                .statusCode(403)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data.totalRowInserted", nullValue())
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());
    }

    @Test(testName = "Station_N_013_CreateMasterStation_ReqBodyInvalid")
    public void createMasterStationReqBodyInvalid() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_master_station_success.json");
        request.remove("stations");
        String usernameMasterStation = ListMasterStationTest.getUsernameMasterStation();
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(), ConstantParameter.PATH_PARAM_MODULE_MASTER_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post("/" + usernameMasterStation)
                .then()
                .statusCode(400)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data.totalRowInserted", nullValue())
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());
    }

    @Test(testName = "Station_N_014_CreateMasterStation_MoreThan_FourStation")
    public void createMasterStationMoreThanFourStation() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_master_station_success.json");

        JSONObject joUUid = new JSONObject();
        joUUid.put("uuid", UUID.randomUUID().toString());

        JSONObject jo = new JSONObject();
        jo.put("station", joUUid);

        JSONArray ja = new JSONArray();
        ja.put(jo);

//        JSONObject mainObject = new JSONObject();
//        mainObject.put("stations", ja);
//        System.out.println(mainObject);
        request.put("stations", ja);
        String jsonBody = request.toJSONString()
                .replaceAll("\\[\"","[")
                .replaceAll("\"]","]")
                .replaceAll("\\\\", "");
        System.out.println(jsonBody);
        String usernameMasterStation = ListMasterStationTest.getUsernameMasterStation();
        RequestSpecification requestSpecification = Utility.
                createRequestWithBodyAndHeaders(jsonBody, ConstantParameter.PATH_PARAM_MODULE_MASTER_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification)
                .log().all().post("/" + usernameMasterStation)
                .then()
                .statusCode(400)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data.totalRowInserted", nullValue())
                .extract();
        System.out.println(extract.response().getBody().prettyPrint());
    }

    @AfterMethod
    public void clearData() {
        Utility.getClearData();
    }

}
