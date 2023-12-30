package starWarsSuites;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Planet extends TestBase {
    @Test(description = "Evaluate the color of the character")
    public void testPlanet() {
        //Variable definition
        String endpoint = "https://swapi.dev/api/people/2/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        //Check the success response
        Response characterResponse = given().headers(headers).when().get(endpoint);
        Assert.assertEquals(characterResponse.statusCode(), 200);

        String characterResponseString = characterResponse.getBody().asString();
        JsonPath js = new JsonPath(characterResponseString);

        List<String> films = js.getList("films");

        // Get the details of the first planet in the last film
        Response filmResponse = given().headers(headers).when().get(films.get(films.size() - 1));
        Assert.assertEquals(filmResponse.statusCode(), 200);

        String filmResponseString = filmResponse.getBody().asString();
        js = new JsonPath(filmResponseString);
        List<String> planets = js.getList("planets");

        Response planetResponse = given().headers(headers).when().get(planets.get(0));
        Assert.assertEquals(planetResponse.statusCode(), 200);

        String planetResponseString = planetResponse.getBody().asString();
        js = new JsonPath(planetResponseString);
        String gravity = js.get("gravity");
        String terrain = js.get("terrain");

        // Check the gravity and terrain
        Assert.assertEquals(gravity, "1 standard");
        Assert.assertEquals(terrain, "desert");
    }
}
