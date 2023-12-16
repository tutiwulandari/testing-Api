package com.so.functionaltest.dinascode;

import com.so.invokesapi.LoginAPI;
import com.so.util.ConstantParameter;
import com.so.util.Utility;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
public class ListDinasCodeTest {
    @Test(testName = "TC01", description = "Verify Get List DinasCode Success")
    public void getListDinasCode() {
        RequestSpecification requestSpecification = Utility.createRequest(ConstantParameter.PATH_PARAM_MODULE_GET_DINAS_CODE_STATION);
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        requestSpecification.header("Authorization", "Bearer " + tokenWhenUsingLogin);
        ExtractableResponse<Response> extract = given(requestSpecification).get().then().
                statusCode(200)
                .body("version", equalTo("2.1.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("data", notNullValue())
                .extract();
        extract.response().prettyPrint();
//        System.out.println(extract.response().getBody().prettyPrint());
    }

}
