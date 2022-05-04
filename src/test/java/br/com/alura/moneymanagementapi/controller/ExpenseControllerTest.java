package br.com.alura.moneymanagementapi.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;

class ExpenseControllerTest {
    @BeforeEach
    void setUp() {
        baseURI = "http://localhost";
        port = 8080;
        basePath = "/expenses";
    }

    @Test
    void returnNoContent_WhenExpenseListIsEmpty() throws Exception {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void returnCreated_WhenSaveAValidExpense() {

        given()
                .body("{\n" +
                        "    \"description\":\"College\",\n" +
                        "    \"value\":\"10000\",\n" +
                        "    \"date\":\"01-06-2022\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/")
        .then()
            .assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void returnConflict_WhenExpenseAlreadyExists() {

        given()
                .body("{\n" +
                        "    \"description\":\"College\",\n" +
                        "    \"value\":\"10000\",\n" +
                        "    \"date\":\"01-06-2022\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/")
        .then()
                .assertThat()
                    .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void returnOK_WhenAtLeastOneExpenseIsPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnOK_WhenAtLeastOneExpenseByMonthIsPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/2022/06")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnNoContent_WhenExpenseListByMonthIsEmpty() throws Exception {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/3000/01")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void returnOK_WhenExpenseByIdIsPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/1")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnNotFound_WhenExpenseByIdIsNotPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/100")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void returnOK_WhenUpdateExpenseByIdIsAllRight() {

        given()
                .body("{\n" +
                        "    \"description\":\"College\",\n" +
                        "    \"value\":\"10000\",\n" +
                        "    \"date\":\"01-07-2022\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .put("/1")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnNotFound_WhenUpdateExpenseByIdIsNotPresent() {

        given()
                .body("{\n" +
                        "    \"description\":\"College\",\n" +
                        "    \"value\":\"10000\",\n" +
                        "    \"date\":\"01-07-2022\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .put("/10")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void returnConflict_WhenUpdateExpenseByIdAlreadyExists() {

        given()
                .body("{\n" +
                        "    \"description\":\"College\",\n" +
                        "    \"value\":\"10000\",\n" +
                        "    \"date\":\"01-07-2022\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .put("/1")
        .then()
            .assertThat()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void returnOK_WhenDeleteExpenseByIdIsPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
            .delete("/1")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnNotFound_WhenDeleteExpenseByIdIsNotPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/100")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}