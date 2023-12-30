package starWarsSuites;

import io.restassured.response.Response;
import lib.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FilmNotFound extends TestBase {
    @Test(description = "Evaluate film not found")
    public void testFilmNotFound() {
        //Variable definition
        String endpoint = "https://swapi.dev/api/films/7/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");


        Response response = given().headers(headers).when().get(endpoint);
        Assert.assertEquals(response.statusCode(), 404);
    }
}
