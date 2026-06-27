package com.petstore.api;

import com.petstore.config.SpecBuilder;
import com.petstore.constants.ApiEndpoints;
import com.petstore.models.request.UserRequest;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static io.restassured.RestAssured.given;

public class UserAPI {

    private static final Logger log = LoggerFactory.getLogger(UserAPI.class);

    public Response createUser(UserRequest user) {
        log.info("POST /user - username: {}", user.getUsername());
        return given().spec(SpecBuilder.getDefaultSpec()).body(user)
                .when().post(ApiEndpoints.USER)
                .then().log().all().extract().response();
    }

    public Response createUsersWithArray(List<UserRequest> users) {
        log.info("POST /user/createWithArray - count: {}", users.size());
        return given().spec(SpecBuilder.getDefaultSpec()).body(users)
                .when().post(ApiEndpoints.USER_CREATE_ARRAY)
                .then().log().all().extract().response();
    }

    public Response createUsersWithList(List<UserRequest> users) {
        log.info("POST /user/createWithList - count: {}", users.size());
        return given().spec(SpecBuilder.getDefaultSpec()).body(users)
                .when().post(ApiEndpoints.USER_CREATE_LIST)
                .then().log().all().extract().response();
    }

    public Response loginUser(String username, String password) {
        log.info("GET /user/login - username: {}", username);
        return given().spec(SpecBuilder.getNoAuthSpec())
                .queryParam("username", username)
                .queryParam("password", password)
                .when().get(ApiEndpoints.USER_LOGIN)
                .then().log().all().extract().response();
    }

    public Response logoutUser() {
        log.info("GET /user/logout");
        return given().spec(SpecBuilder.getNoAuthSpec())
                .when().get(ApiEndpoints.USER_LOGOUT)
                .then().log().all().extract().response();
    }

    public Response getUserByName(String username) {
        log.info("GET /user/{}", username);
        return given().spec(SpecBuilder.getNoAuthSpec())
                .pathParam("username", username)
                .when().get(ApiEndpoints.USER_BY_USERNAME)
                .then().log().all().extract().response();
    }

    public Response updateUser(String username, UserRequest user) {
        log.info("PUT /user/{}", username);
        return given().spec(SpecBuilder.getDefaultSpec())
                .pathParam("username", username).body(user)
                .when().put(ApiEndpoints.USER_BY_USERNAME)
                .then().log().all().extract().response();
    }

    public Response deleteUser(String username) {
        log.info("DELETE /user/{}", username);
        return given().spec(SpecBuilder.getDefaultSpec())
                .pathParam("username", username)
                .when().delete(ApiEndpoints.USER_BY_USERNAME)
                .then().log().all().extract().response();
    }
}
