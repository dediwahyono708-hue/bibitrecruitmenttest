package com.example.qa.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIClient {

    private static String requestBody;

    public static void setBody(String body) {
        requestBody = body;
    }

    public static Response post(String url) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .post(url)
                .then()
                .extract()
                .response();
    }

    public static Response get(String url) {
        return RestAssured.given()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public static Response delete(String url) {
        return RestAssured.given()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    public static Response put(String url) {
        return RestAssured.given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .put(url)
                .then()
                .extract()
                .response();
    }
}
