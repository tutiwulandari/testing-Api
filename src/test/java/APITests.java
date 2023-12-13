import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class APITests {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://dev.lrtosis.com:30000";
    }


//    @Test
//    void test1() {
//        Response response = get("https://reqres.in/api/users?page=2");
//        System.out.println("Status Code: " + response.getStatusCode());
//        System.out.println("Response:  " + response.asString());
//        System.out.println("Body: " + response.getBody().asString());
//        System.out.println("Time Taken: " + response.getTime());
//        System.out.println("Header: " + response.getHeader("content-type"));
//
//        int statusCode = response.getStatusCode();
//        Assert.assertEquals(statusCode, 200);
//    }
//    @Test
//    void testGetApi() {
//        baseURI = "https://reqres.in/api";
//        given().
//                get("/users?page=2").
//                then().
//                statusCode(200).
//                contentType(ContentType.JSON).
//                body("data[4].first_name", Matchers.equalTo("George"));
//
//    }

//    @Test
//    void test2() {
//        given().get("https://reqres.in/api/users?page=2").then().statusCode(200);
//
//    }
//
//    @Test
//    void test_ResponseHeaderData_ShouldBeCorrect() {
//        given().
//                when().
//                get("https://reqres.in/api/users?page=2").
//                then().
//                assertThat().
//                statusCode(200).
//                and().
//                contentType(ContentType.JSON);
//    }
//    @Test
//    public void test_CreateStasiun_SUCCESS() {
//        String url = "https://mock.apidog.com/m1/416892-0-22e9f779/v2/stasiun";
//
////        String jsonString = "{\"version\": \"2.0.0\",\n" +
////                "    \"namaStasiun\": \"Stasiun Cawang\",\n" +
////                "    \"kodeStasiun\": {\n" +
////                "        \"internal\": \"JTM\",\n" +
////                "        \"eksternal\": \"CAW\"\n" +
////                "    },\n" +
////                "    \"lokasiStasiun\": {\n" +
////                "        \"latitude\": \"-6.1311264\",\n" +
////                "        \"longitude\": \"106.5394471\"\n" +
////                "    },\n" +
////                "    \"operasionalStasiun\": [\n" +
////                "        {\n" +
////                "            \"bukaStasiun\": \"05:00:00\",\n" +
////                "            \"tutupStasiun\": \"23:00:00\",\n" +
////                "            \"waktuBerlakuEfektif\": {\n" +
////                "                \"mulai\": \"2023-12-25\",\n" +
////                "                \"selesai\": \"2023-12-26\"\n" +
////                "            }\n" +
////                "        }\n" +
////                "    ]}";
//        RequestSpecification req = RestAssured.given();
//        req.contentType(ContentType.JSON);
//        req.baseUri(url);
//        Response response = req.post();
//        System.out.println(response.asString());
//        System.out.println(response.getStatusCode());
//        ValidatableResponse validatableResponse = response.then();
//        validatableResponse.statusCode(200);
//
//
//    }

    @Test
    void testPost() {
//        Map<String, String> users = new HashMap<>();
//        users.put("name", "Wulan");
//        users.put("Job", "QA");

        JSONObject request = new JSONObject();
        request.put("name", "Wulan");
        request.put("Job", "QA");
//        System.out.println(request.toJSONString());

        baseURI = "https://reqres.in/api";
        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().
                post("/users").
                then().statusCode(201).log().all();


    }

//    @Test
//    void testLogin() {
//        baseURI = "https://dev.lrtosis.com:30000";
//        JSONObject request = new JSONObject();
//
//        request.put("username", "demo-ta");
//        request.put("password", "75673aad502f3dd315172de4f34d58cc");
//        request.put("deviceType", "WEB");
//        request.put("deviceId", "d2131dewfe@!$@#");
//        var jsonString = request.toJSONString();
//        given().relaxedHTTPSValidation()
//                .header("Content-Type", "application/json")
//                .body(jsonString).log().all().
//                when().post("/v2/login").
//                then().log().all().statusCode(200);
//    }

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
        String token = validatableResponse.extract().asPrettyString();
        System.out.println(token);

    }
}
