import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetBookingsWithDataProvider {
    @DataProvider(name="dataProvider")
    public Object[][] dataProvider (){
        return new Object[][] {
                {4}, {5}};
    }

    @Test(dataProvider = "dataProvider")
    public void getSpecificBooking(int bookingsId){
        given()
                .log().all().
                when()
                .get("https://restful-booker.herokuapp.com/booking/" +bookingsId)
                .then().statusCode(200).
                log().all();

    }
}
