import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class LoginTest {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://dev.lrtosis.com:30000";

    }
    @Test
    void testLogin() {
        baseURI = "https://dev.lrtosis.com:30000";
        JSONObject request = new JSONObject();
        ValidatableResponse validatableResponse;

        request.put("username", "demo-ta");
        request.put("password", "75673aad502f3dd315172de4f34d58cc");
        request.put("deviceType", "WEB");
        request.put("deviceId", "d2131dewfe@!$@#");
        validatableResponse = given().relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(request.toJSONString()).
                when().post("/v2/login").
                then().statusCode(200);
//        System.out.println("Response :" + validatableResponse.extract().asPrettyString());
//        String token = validatableResponse.extract().asPrettyString();
//        System.out.println(token);

    }
}
