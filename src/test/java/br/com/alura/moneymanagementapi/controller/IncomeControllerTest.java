package br.com.alura.moneymanagementapi.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

class IncomeControllerTest {
    @BeforeEach
    void setUp() {
        baseURI = "http://localhost";
        port = 8080;
        basePath = "/incomes";
    }

    @Test
    void returnNoContent_WhenIncomeListIsEmpty() throws Exception {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void returnCreated_WhenSaveAValidIncome() {

        given()
                .body("{\n" +
                        "    \"description\":\"Salary\",\n" +
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
    void returnConflict_WhenIncomeAlreadyExists() {

        given()
                .body("{\n" +
                        "    \"description\":\"Salary\",\n" +
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
    void returnOK_WhenAtLeastOneIncomeIsPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnOK_WhenAtLeastOneIncomeByMonthIsPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/2022/06")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnNoContent_WhenIncomeListByMonthIsEmpty() throws Exception {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/3000/01")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void returnOK_WhenIncomeByIdIsPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/1")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnNotFound_WhenIncomeByIdIsNotPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/100")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void returnOK_WhenUpdateIncomeByIdIsAllRight() {

        given()
                .body("{\n" +
                        "    \"description\":\"Salary\",\n" +
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
    void returnNotFound_WhenUpdateIncomeByIdIsNotPresent() {

        given()
                .body("{\n" +
                        "    \"description\":\"Salary\",\n" +
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
    void returnConflict_WhenUpdateIncomeByIdAlreadyExists() {

        given()
                .body("{\n" +
                        "    \"description\":\"Salary\",\n" +
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
    void returnOK_WhenDeleteIncomeByIdIsPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
            .delete("/1")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void returnNotFound_WhenDeleteIncomeByIdIsNotPresent() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/100")
        .then()
            .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}