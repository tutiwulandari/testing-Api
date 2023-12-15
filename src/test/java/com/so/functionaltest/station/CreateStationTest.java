package com.so.functionaltest.station;

import com.so.invokesapi.LoginAPI;
import com.so.invokesapi.StationAPI;
import com.so.util.Utility;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateStationTest {


    @Test(testName = "TC01", description = "Verify Create Station Success")
    public void createStationSuccess() {
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + tokenWhenUsingLogin);
        var response = StationAPI.invokeCreateStation(headers, Utility.buildRequestJSON("request/station/create_station_success.json"));
        response
                .statusCode(200)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(true))
                .body("timestamp", notNullValue())
                .body("message", notNullValue());

    }


    @Test(testName = "TC02", description = "Verify When Token Invalid, part authorization blank")
    public void createStationTokenIsBlank() {
        var response = StationAPI.invokeCreateStation(Collections.EMPTY_MAP, Utility.buildRequestJSON("request/station/create_station_success.json"));
        response
                .statusCode(401)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("error", notNullValue());
    }

    @Test(testName = "TC03", description = "Verify Request Body Invalid")
    public void createStationRequestBodyInvalid() throws IOException, ParseException {
        JSONObject request = Utility.buildRequestJSON("request/station/create_station_success.json");
        request.remove("name");
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + tokenWhenUsingLogin);
        var response = StationAPI.invokeCreateStation(headers, request);
        response
                .statusCode(400)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue())
                .body("error", notNullValue());
    }


}
