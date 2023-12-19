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
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class ListMasterStationTest {

    @Test(testName = "Station_P_003_GetList_MasterStation_Success", description = "Verify Get List Master Station Success")
    public void getListMasterStation() {
        RequestSpecification requestSpecification = Utility
                        .createRequestWithHeader(ConstantParameter.PATH_PARAM_MODULE_GET_MASTER_STATION,
                Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);

        ExtractableResponse<Response> extract = given(requestSpecification).get().then()
                .statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data", notNullValue())
                .extract();
        // extract.response().prettyPrint();
        System.out.println(extract.response().getBody().prettyPrint());
    }


    @Test(testName = "Station_N_013_GetList_MasterStation_TokenInvalid", description = "Verify Get List Master Station Token Invalid")
    public void getListMasterStationTokenIsBlank() {
        RequestSpecification requestSpecification = Utility
                .createRequestWithHeader(ConstantParameter.PATH_PARAM_MODULE_GET_MASTER_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
        ExtractableResponse<Response> extract = given(requestSpecification).get().then()
                .statusCode(401)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract();
        // extract.response().prettyPrint();
        System.out.println(extract.response().getBody().prettyPrint());
    }

    @Test(testName = "Station_N_014_MasterStation_AuthInvalid", description = "Verify Get List Master Station Authentication Invalid")
    public void getListMasterStationAuthInvalid() {
        RequestSpecification requestSpecification = Utility
                .createRequestWithHeader(ConstantParameter.PATH_PARAM_MODULE_GET_MASTER_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "MASTER.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification).get().then()
                .statusCode(403)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract();
        // extract.response().prettyPrint();
        System.out.println(extract.response().getBody().prettyPrint());
    }
    public static String getUsernameMasterStation() {
        RequestSpecification requestSpecification = Utility.createRequestWithHeader(ConstantParameter.PATH_PARAM_MODULE_GET_MASTER_STATION,
                        Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        Response response = given(requestSpecification).log().all().get();
        System.out.println(response.getBody().prettyPrint());
        ValidatableResponse validatableResponse = given(requestSpecification).relaxedHTTPSValidation()
                .log().all()
                .get()
                .then()
                .statusCode(200);
        ResponseBodyExtractionOptions body = validatableResponse.extract().body();
        JsonPath jsonPath = body.jsonPath();
        System.out.println("body " +  jsonPath);

        ArrayList<String> listUsername = jsonPath.get("data.content.user.username");

        return listUsername.get(0);
    }
}
