package SimpleBooksAPI;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.fge.jackson.NodeType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Random;
import APITest.Utils.Order;
import APITest.Utils.OrderResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;


public class BooksEp {

    private String generateRandomEmail() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Generar un número aleatorio entre 0 y 9999
        return "user" + randomNumber + "@example.com";
    }
 
    private String token;

    // Getting the token from the API
    // This is done by creating a new API client with the given name and email
 
    @BeforeClass (groups = { "EpBooks" })
    public void setup() {
        // Generar un correo electrónico aleatorio
        String randomEmail = generateRandomEmail();
        System.out.println("Email generado: " + randomEmail);
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
        Response response = given()
                .contentType("application/json")
                .body("{\"clientName\": \"Postman\", \"clientEmail\": \"" + randomEmail + "\"}")
                .when()
                .post("/api-clients/")
                .then()
                .statusCode(201)
                .extract()
                .response();
 
        token = response.jsonPath().getString("accessToken");
    }

    @Test(groups = { "EpBooks" })
    @Severity(SeverityLevel.CRITICAL)
    @Owner("aflofiego")
    @Tag("HappyPath")
    @Epic("SimpleBooksAPI")
    @Feature("EpBooks")
    @Story("Get All Books")
    @Description("Cuando se hace una solicitud GET a /books, se espera un código de estado 200 y una respuesta válida según el esquema JSON.")
    public void validateGetAllBooks() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Get All Books test case - 200"));
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/books")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/books-schema.json"))
                .log().all();
    }

    @Test(groups = { "EpBooks" })
    @Severity(SeverityLevel.NORMAL)
    @Owner("aflofiego")
    @Tag("HappyPath")
    @Epic("SimpleBooksAPI")
    @Feature("EpBooks")
    @Story("Get All Books")
    @Description("Cuando se hace una solicitud GET a /books, se espera un código de estado 200, se evalua el tiempo de respuesta menor a 2.9 segundos y una respuesta válida según el esquema JSON.")
    public void validateGetAllBooksWithResponseTime() {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Get All Books test case - 200 with response time"));
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/books")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/books-schema.json"))
                .time(lessThan(2900L)) // Validamos que la respuesta sea menor a 2.9 segundos.
                .log().all();
    }

}
