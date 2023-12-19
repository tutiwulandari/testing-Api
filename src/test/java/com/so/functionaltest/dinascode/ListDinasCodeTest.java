package com.so.functionaltest.dinascode;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import com.so.util.Utility;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class ListDinasCodeTest {
    
    @Test(testName = "Station_P_003_GetList_CodeStation_Success", description = "Verify Get List DinasCode Success")
    public void getListDinasCode() {
        RequestSpecification requestSpecification = Utility.createRequestWithHeader(ConstantParameter.PATH_PARAM_MODULE_GET_DINAS_CODE_STATION,
                Collections.singletonMap("TESTING-LOGIN-USER", "STATION_OPERATOR.admin"));
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

    @Test(testName = "Station_N_009_GetList_DinasCode_TokenInvalid", description = "Verify Get Dinas Code when token invalid")
    public void getCodeStationTokenInvalid()  {
        RequestSpecification requestSpecification = Utility.createRequestWithHeader(ConstantParameter.PATH_PARAM_MODULE_GET_DINAS_CODE_STATION,
                Collections.singletonMap("TESTING-LOGIN-USER", "ADMIN.admin"));
        ExtractableResponse<Response> extract = given(requestSpecification).get().then().
                statusCode(401)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract();
        extract.response().prettyPrint();
    }

    @Test(testName = "Station_N_010_GetList_DinasCode_AuthInvalid", description = "Verify Get List Dinas Code when token invalid")
    public  void getCodeStationAuthInvalid() {
        RequestSpecification requestSpecification = Utility.createRequestWithHeader(ConstantParameter.PATH_PARAM_MODULE_GET_DINAS_CODE_STATION,
                Collections.singletonMap("TESTING-LOGIN-USER", "MASTER.admin"));
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification).get().then().
                statusCode(403)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .extract();
        System.out.println(extract.response().prettyPrint());
    }

    @BeforeMethod
    public void createStationSuccess() throws IOException, ParseException {
        CreateDinasCodeTest.createDinasCodeSuccess();

    }
    @AfterMethod
    public void clearData() {
        Utility.getClearData();

    }

}
