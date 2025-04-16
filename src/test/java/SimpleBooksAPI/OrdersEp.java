package SimpleBooksAPI;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
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
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OrdersEp {

        // Metodo para generar un correo electrónico aleatorio
        private String token;

        private String generateRandomEmail() {
                Random random = new Random();
                int randomNumber = random.nextInt(10000); // Generar un número aleatorio entre 0 y 9999
                return "user" + randomNumber + "@example.com";
        }

        // Getting the token from the API
        // This is done by creating a new API client with the given name and email

        @BeforeClass(groups = { "EpOrders", "EpAuth" })
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

        // Test de validacion sobre el EP de autenticacion

        @Test(groups = { "EpOrders" })
        @Severity(SeverityLevel.MINOR)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpAuth")
        @Story("/api-clients")
        @Description("Validar que la respuesta del EP /api-clients auth invalido - status code 400")
        public void validarStatusCode401CuandoElTokenEsInvalido() {
                Allure.getLifecycle().updateTestCase(testResult -> testResult
                                .setName("/api-clients auth invalid test case validation  - status code 400"));
                RestAssured.baseURI = "https://simple-books-api.glitch.me";

                String token = "tenemos-un-token-inválido";
                String cuerpoSolicitud = "{\n" +
                                "    \"bookId\": 1,\n" +
                                "    \"customerName\": \"Perro Miner\"\n" +
                                "}";

                given()
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + token)
                                .body(cuerpoSolicitud)
                                .when()
                                .post("/orders")
                                .then()
                                .assertThat()
                                .statusCode(401)
                                .log().all();
        }

        @Test(groups = { "EpAuth" })
        @Severity(SeverityLevel.MINOR)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpAuth")
        @Story("/api-clients")
        @Description("Validar que la respuesta del EP /api-clients body vacio - status code 400")
        public void validarCuerpoVacioParaObtenerToken() {
                Allure.getLifecycle().updateTestCase(testResult -> testResult
                                .setName("/api-clients auth empty body test case validation - status code 400"));
                RestAssured.baseURI = "https://simple-books-api.glitch.me";

                // Cuerpo de solicitud sin campos obligatorios
                String cuerpoDeSolicitudInvalido = "{}";

                given()
                                .header("Content-Type", "application/json")
                                .body(cuerpoDeSolicitudInvalido)
                                .when()
                                .post("/api-clients")
                                .then()
                                .assertThat()
                                .statusCode(400)
                                .body("error", equalTo("Invalid or missing client name."))
                                .log().all();

        }

        @Test(groups = { "EpAuth" })
        @Severity(SeverityLevel.CRITICAL)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpAuth")
        @Story("/api-clients")
        @Description("Validar que la respuesta del EP /api-clients sin clientEmail - status code 400")
        public void validarCuerpoSoloNombreParaObtenerToken() {
                Allure.getLifecycle().updateTestCase(testResult -> testResult
                                .setName("/api-clients auth test case validation - status code 400"));
                RestAssured.baseURI = "https://simple-books-api.glitch.me";

                // Cuerpo de solicitud sin campos obligatorios
                String cuerpoDeSolicitudSoloNombre = "{ \n" +
                                "    \"clientName\": \"ElPerris\"\n" +
                                "}";

                given()
                                .header("Content-Type", "application/json")
                                .body(cuerpoDeSolicitudSoloNombre)
                                .when()
                                .post("/api-clients")
                                .then()
                                .assertThat()
                                .statusCode(400)
                                .body("error", equalTo("Invalid or missing client email."))
                                .log().all();

        }

        @Test(groups = { "EpAuth" })
        @Severity(SeverityLevel.CRITICAL)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpAuth")
        @Story("/api-clients")
        @Description("Validar que la respuesta del EP /api-clients sin clientName - status code 422")
        public void validarCuerpoSoloEmailParaObtenerToken() {
                Allure.getLifecycle().updateTestCase(testResult -> testResult
                                .setName("/api-clients auth test case validation - status code 422"));
                RestAssured.baseURI = "https://simple-books-api.glitch.me";

                // Cuerpo de solicitud sin campos obligatorios
                String cuerpoDeSolicitudSoloEmail = "{ \n" +
                                "    \"clientEmail\": \"ElPerris\"\n" + System.currentTimeMillis() + "@example.com\"\n"
                                +
                                "}";

                given()
                                .header("Content-Type", "application/json")
                                .body(cuerpoDeSolicitudSoloEmail)
                                .when()
                                .post("/api-clients")
                                .then()
                                .assertThat()
                                .statusCode(422)
                                .log().all();

        }

        // Test de validacion sobre el EP Orders

        @Test(groups = { "EpOrders" })
        @Severity(SeverityLevel.NORMAL)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpOrders")
        @Story("/Orders")
        @Description("Validar que la respuesta del EP /Orders sea 200 cuando el token es valido")
        public void testTokenBooks() {
                Allure.getLifecycle().updateTestCase(
                                testResult -> testResult.setName("/Orders test case with auth - status code 200"));
                given()
                                .header("Authorization", "Bearer " + token)
                                .when()
                                .get("/orders")
                                .then()
                                .log().all()
                                .statusCode(200);

        }

        @Test(groups = { "EpOrders" })
        @Severity(SeverityLevel.NORMAL)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpOrders")
        @Story("/Orders")
        @Description("Patch del EP /Orders - status code 200")
        public void submitOrder() {
                Allure.getLifecycle().updateTestCase(testResult -> testResult
                                .setName("/Orders patch test case with auth - status code 200"));
                // Crear un objeto Order
                Order newOrder = new Order(1, "John");
                // Serializar y enviar el objeto Order como JSON en una solicitud POST
                OrderResponse orderResponse = given()
                                .header("Authorization", "Bearer " + token)
                                .contentType("application/json")
                                .body(newOrder)
                                .when()
                                .post("/orders")
                                .then()
                                .statusCode(201)
                                .extract()
                                .as(OrderResponse.class); // Deserialización de la respuesta

                // Suponiendo que la respuesta contiene un campo orderId
                System.out.println("Order ID: " + orderResponse.getOrderId());
        }

        @Test(groups = { "EpOrders" })
        @Severity(SeverityLevel.CRITICAL)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpOrders")
        @Story("/Orders")
        @Description("Validar que la respuesta del EP /Orders sea 200")
        public void testGetOrdersEndpoint() {
                Allure.getLifecycle().updateTestCase(
                                testResult -> testResult.setName("/Orders test case - status code 200"));
                given()
                                .header("Authorization", "Bearer " + token)
                                .when()
                                .get("/orders")
                                .then()
                                .statusCode(200)
                                .log().all();
        }

        @Test(groups = { "EpOrders" })
        @Severity(SeverityLevel.CRITICAL)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpOrders")
        @Story("/Orders")
        @Description("Validar que la edición de una orden sea idempotente.")
        public void validarIdempotenciaPUT() {
                Allure.getLifecycle().updateTestCase(
                                testResult -> testResult.setName("/Orders patch test case - idempotence 200"));
                RestAssured.baseURI = "https://simple-books-api.glitch.me";
                String orderId = crearOrden(token);
                System.out.println(orderId);

                String cuerpoActualización = "{\n" +
                                "    \"customerName\": \"Nuevo Nombre\"\n" +
                                "}";
                // Primera actualización
                Response primerPUT = given()
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + token)
                                .body(cuerpoActualización)
                                .when()
                                .patch("/orders/" + orderId)
                                .then()
                                .assertThat()
                                .statusCode(204)
                                .extract().response();

                // Segunda actualización
                Response segundoPUT = given()
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + token)
                                .body(cuerpoActualización)
                                .when()
                                .patch("/orders/" + orderId)
                                .then()
                                .assertThat()
                                .statusCode(204)
                                .log().all()
                                .extract().response();

                // Validamos que ambas respuestas son iguales
                Assert.assertEquals(primerPUT.getStatusCode(), segundoPUT.getStatusCode());
                Assert.assertEquals(primerPUT.getTime(), segundoPUT.getTime(), 1000);
        }

        @Test(groups = { "EpOrders" })
        @Severity(SeverityLevel.NORMAL)
        @Owner("aflofiego")
        @Tag("Smoke")
        @Epic("SimpleBooksAPI")
        @Feature("EpOrders")
        @Story("/Orders")
        @Description("Validar que la eliminación de una orden sea idempotente.")
        public void validarIdempotenciaDELETE() {
                Allure.getLifecycle().updateTestCase(
                                testResult -> testResult.setName("/Orders delete test case - idempotence 200"));
                RestAssured.baseURI = "https://simple-books-api.glitch.me";
                String orderId = crearOrden(token);

                // Primera eliminación
                Response primerDelete = given()
                                .header("Authorization", "Bearer " + token)
                                .when()
                                .delete("/orders/" + orderId)
                                .then()
                                .assertThat()
                                .statusCode(204)
                                .log().all()
                                .extract().response();
                // Segunda eliminación
                Response segundoDelete = given()
                                .header("Authorization", "Bearer " + token)
                                .when()
                                .delete("/orders/" + orderId)
                                .then()
                                .assertThat()
                                .statusCode(404)
                                .log().all()
                                .extract().response();

                // La primera eliminación fue exitosa (204) y la segunda no (un 404)
                Assert.assertEquals(primerDelete.getStatusCode(), 204);
                Assert.assertEquals(segundoDelete.getStatusCode(), 404);

        }

        // Método auxiliar para crear una orden
        private String crearOrden(String token) {
                RestAssured.baseURI = "https://simple-books-api.glitch.me";
                String cuerpoOrden = "{\n" +
                                "    \"bookId\": 1,\n" +
                                "    \"customerName\": \"Patito\"\n" +
                                "}";
                Response respuesta = given()
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + token)
                                .body(cuerpoOrden)
                                .when()
                                .post("/orders")
                                .then()
                                .assertThat()
                                .statusCode(201)
                                .extract().response();
                return respuesta.jsonPath().getString("orderId");
        }

}
