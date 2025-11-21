package com.example.qa.helpers;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BibitApiHelper {

    public static class LoginResponse {
        public String accessToken;
        public String refreshToken;
        public String acExpire;
        public String rfExpire;
    }

    public static LoginResponse login(String phone, String otp) {

        Response res = given()
                .baseUri("https://api.bibit.id")
                .header("Content-Type", "application/json")
                .body("{\"phone\":\"" + phone + "\", \"otp\":\"" + otp + "\"}")
                .post("/v1/auth/otp/verify")
                .then()
                .statusCode(200)
                .extract().response();

        LoginResponse login = new LoginResponse();
        login.accessToken = res.jsonPath().getString("data.access_token");
        login.refreshToken = res.jsonPath().getString("data.refresh_token");
        login.acExpire = res.jsonPath().getString("data.access_expire");
        login.rfExpire = res.jsonPath().getString("data.refresh_expire");

        return login;
    }
}
