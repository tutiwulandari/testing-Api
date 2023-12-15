package com.so.functionaltest.station;

import com.so.invokesapi.LoginAPI;
import com.so.invokesapi.StationAPI;
import com.so.util.Utility;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UpdateStationTest {


    @Test(testName = "TC15", description = "Edit Stasiun when code.internal is exist")
    public void updateStationCodeInternalExist() {
        String tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + tokenWhenUsingLogin);
        JSONObject requestCreateStation = Utility.buildRequestJSON("request/station/create_station_success.json");
        var response = StationAPI.invokeCreateStation(headers, requestCreateStation);
        JSONObject code = new JSONObject();
        code.put("internal", "ABC");
        code.put("external", "DEF");
        requestCreateStation.put("code", code);
        tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        headers.put("Authorization", "Bearer " + tokenWhenUsingLogin);
        var responseTwice = StationAPI.invokeCreateStation(headers, requestCreateStation);
        JsonPath jsonPath = responseTwice.extract().response().getBody().jsonPath();
        var uuid = (String) jsonPath.get("data.uuid");

        tokenWhenUsingLogin = LoginAPI.getTokenWhenUsingLogin();
        headers.put("Authorization", "Bearer " + tokenWhenUsingLogin);
        JSONObject jsonObject = Utility.buildRequestJSON("request/station/update_station_success.json");
        var responseUpdate = StationAPI.invokeUpdateStation(headers, jsonObject, uuid);

        responseUpdate
                .statusCode(400)
                .body("version", equalTo("2.0.0"))
                .body("success", equalTo(false))
                .body("timestamp", notNullValue())
                .body("message", notNullValue());
    }

}
