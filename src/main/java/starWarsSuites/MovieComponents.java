package starWarsSuites;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MovieComponents extends TestBase {
    @Test(description = "Evaluate the color of the character")
    public void testMovieComponents() {
        //Variable definition
        String endpoint = "https://swapi.dev/api/people/2/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        //Check the success response
        Response characterResponse = given().headers(headers).when().get(endpoint);
        Assert.assertEquals(characterResponse.statusCode(), 200);

        String characterResponseString = characterResponse.getBody().asString();
        JsonPath js = new JsonPath(characterResponseString);
        String skin_color = js.get("skin_color");
        List<String> films = js.getList("films");

        //Check the skin color is gold
        Assert.assertEquals(skin_color, "gold");

        // Check that the character appears in 6 films
        Assert.assertEquals(films.size(), 6, "Character does not appear in 6 films");

        // Get the details of the second film
        Response filmResponse = given().headers(headers).when().get(films.get(1));
        Assert.assertEquals(filmResponse.statusCode(), 200);

        String filmResponseString = filmResponse.getBody().asString();
        js = new JsonPath(filmResponseString);
        String release_date = js.get("release_date");
        List<String> characters = js.getList("characters");
        List<String> planets = js.getList("planets");
        List<String> starships = js.getList("starships");
        List<String> vehicles = js.getList("vehicles");
        List<String> species = js.getList("species");

        // Check that the release date is in the correct format
        try {
            LocalDate.parse(release_date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            Assert.fail("Release date is not in the correct format", e);
        }

        // Check that the response includes characters, planets, starships, vehicles and species
        Assert.assertTrue(characters.size() > 1, "Film does not include more than one character");
        Assert.assertTrue(planets.size() > 1, "Film does not include more than one planet");
        Assert.assertTrue(starships.size() > 1, "Film does not include more than one starship");
        Assert.assertTrue(vehicles.size() > 1, "Film does not include more than one vehicle");
        Assert.assertTrue(species.size() > 1, "Film does not include more than one species");
    }
}
