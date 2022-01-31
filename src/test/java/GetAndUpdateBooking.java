import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;


public class GetAndUpdateBooking
{
    public int bookingId=2;
    private String token;

    @Test
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
    public void getAllBookings(){
        given()
                .log().all().
                when()
                .get("https://restful-booker.herokuapp.com/booking").
                then().statusCode(200)
                .log().all();
    }

    @Test
    public void updateBooking(){

        String jsonString = "{\"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"}";

        given()
                .log().all().
                baseUri("https://restful-booker.herokuapp.com/booking/" + bookingId).
                contentType(ContentType.JSON).
                header("Content-Type", "Application/json").
                cookie("token", this.token)
                .body(jsonString).
                when()
                .put().
                then()
                .assertThat().statusCode(200)
                .log().all();

    }
}
