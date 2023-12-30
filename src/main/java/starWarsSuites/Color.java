package starWarsSuites;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Color extends TestBase {
    @Test(description = "Evaluate the color of the character")
    public void testColor() {
        //Variable definition
        String endpoint = "https://swapi.dev/api/people/2/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        //Check the success response
        Response characterResponse = given().headers(headers).when().get(endpoint);
        Assert.assertEquals(characterResponse.statusCode(), 200);

        //Get the skin color and list of films
        String characterResponseString = characterResponse.getBody().asString();
        JsonPath js = new JsonPath(characterResponseString);
        String skin_color = js.get("skin_color");
        List<String> films = js.getList("films");

        //Check the skin color is gold
        Assert.assertEquals(skin_color, "gold");

        // Check that the character appears in 6 films
        Assert.assertEquals(films.size(), 6, "Character does not appear in 6 films");
    }
}
