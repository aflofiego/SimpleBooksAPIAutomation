package SimpleBooksAPI;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class BooksSchemaValidation {

    @Test
    public void validateBooksSchema() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
        given()
                .header("Content-type", "application/json")
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/books-schema.json"))
                .log().all();
    }


    @Test
    public void validarStatusCode404() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
 
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/books/999")
                .then()
                .assertThat()
                .statusCode(404);
    }


    @Test
    public void validarStatusCode400CuandoElCuerpoEsInvalido() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
 
        // Obtener el token y asignarlo a la variable
        String token = obtenerToken();
        String cuerpoSolicitud = "{}";
 
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(cuerpoSolicitud)
                .when()
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(400)
                .log().all();
    }

    // Función para obtener el token
    String obtenerToken() {
        String cuerpoSolicitud = "{\n" +
                "    \"clientName\": \"Rogwiler\",\n" +
                "    \"clientEmail\": \"Rogwiler1 @example.com\"\n" +
                "}";
 
        Response respuesta = given()
                .header("Content-Type", "application/json")
                .body(cuerpoSolicitud)
                .when()
                .post("/api-clients")
                .then()
                .extract()
                .response();
 
        return respuesta.jsonPath().getString("accessToken");
    }
}
