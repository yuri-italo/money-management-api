package br.com.alura.moneymanagementapi.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;

class MonthSummaryControllerTest {

    @BeforeEach
    void setUp() {
        baseURI = "http://localhost";
        port = 8080;
        basePath = "/summaries";
    }

    @Test
    void returnOK_WhenGettingAMonthlySummary() {
        given()
                .contentType(ContentType.JSON)
        .when()
            .get("/2022/05")
        .then()
            .assertThat()
                .statusCode(HttpStatus.OK.value());
    }
}