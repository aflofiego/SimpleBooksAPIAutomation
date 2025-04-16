package APITest;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class APITest {
    @Test
    public void testRickAndMortyAPI() {
        with()
            .baseUri("https://rickandmortyapi.com/")
            .get()
            .then()
            .log().all();
    }

    @Test
    public void crearUsuario() {
        String body = "{\n" +
                "    \"nombresuli\": \"Patito\",\n" +
                "    \"chamba\": \"Free Range\"\n" +
                "}";
        given()
                .baseUri("https://reqres.in/api")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void actualizarUsuario() {
        String body = "{\n" +
                "    \"nombresuli\": \"Patitoss\",\n" +
                "    \"chamba\": \"Free Rango\"\n" +
                "}";
        given()
                .baseUri("https://reqres.in/api")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/users/690")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void borrarUsuario() {
        given()
                .baseUri("https://reqres.in/api")
                .when()
                .delete("/users/690")
                .then()
                .statusCode(204)
                .log().all();
    }
    
}
