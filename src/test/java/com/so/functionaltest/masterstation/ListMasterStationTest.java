package com.so.functionaltest.masterstation;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import com.so.util.Utility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class ListMasterStationTest {

    @Test(testName = "Station_P_002_GetList_MasterStation", description = "Verify Get List Master Station Success")
    public void getListMasterStation() {
        RequestSpecification requestSpecification = Utility
                .createRequest(ConstantParameter.PATH_PARAM_MODULE_GET_MASTER_STATION);
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
        System.out.println(extract.response().getBody().prettyPrint());
    }


    public static String getUsernameMasterStation() {
        ValidatableResponse validatableResponse;
        RequestSpecification requestSpecification = Utility
                .createRequest(ConstantParameter.PATH_PARAM_MODULE_GET_MASTER_STATION);
        validatableResponse = given(requestSpecification).relaxedHTTPSValidation()
                .log().all()
                .get()
                .then()
                .statusCode(200);
        ResponseBodyExtractionOptions body = validatableResponse.extract().body();
        JsonPath jsonPath = body.jsonPath();
        ArrayList<String> listUsername = jsonPath.get("data.content.stationMaster.user.username");
        String usernameMasterStation = listUsername.get(0);

        return usernameMasterStation;
    }
}
