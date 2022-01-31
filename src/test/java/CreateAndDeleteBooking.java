import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;


public class CreateAndDeleteBooking
{
    private String token;

    @BeforeMethod
    public void generateToken (){
        String postData= "{\"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"}";

        JsonPath jsonPath =
                given()
                        .body(postData)
                        .contentType(ContentType.JSON)
                        .log().all().
                        when()
                        .post("https://restful-booker.herokuapp.com/auth")
                        .then().log().all()
                        .extract().jsonPath();

        this.token = jsonPath.get("token");
    }

    @Test
    public void postCreateBooking (){

        String requestBody= "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        given()
                .log().all()
                .body(requestBody).contentType(ContentType.JSON). header("Content-Type", "Application/json").
                log().all().
                when().
                post("https://restful-booker.herokuapp.com/booking").then().statusCode(200).log().all();
    }

    @Test
    public void deleteBooking (){
        given()
                .log().all().
                baseUri("https://restful-booker.herokuapp.com/booking/2").
                header("Content-Type", "Application/json").
                cookie("token", this.token).
                when().delete().then().
                statusCode(201).log().all();

    }

}
