package com.so.functionaltest.masterstation;

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
public class CreateMasterStationTest {

    @Test(testName = "Station_P_004_CreateMasterStasion_Success", description = "Verify Create Master Station Success")
    public void createMasterStationSuccess() throws IOException, ParseException {
        //657e4800-e141-4000-8000-b30f59d92972

        JSONObject request = Utility.buildRequestJSON("request/station/create_master_station_success.json");
        String usernameMasterStation = ListMasterStationTest.getUsernameMasterStation();
        RequestSpecification requestSpecification =  Utility.
                createRequestWithBodyAndHeaders(request.toJSONString(),ConstantParameter.PATH_PARAM_MODULE_MASTER_STATION,
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


    @AfterMethod
    public void clearData() {
        Utility.getClearData();
    }

}
