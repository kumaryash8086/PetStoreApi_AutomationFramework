package com.petstore.api;

import com.petstore.config.SpecBuilder;
import com.petstore.constants.ApiEndpoints;
import com.petstore.models.request.StoreOrderRequest;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.given;

public class StoreAPI {

    private static final Logger log = LoggerFactory.getLogger(StoreAPI.class);

    public Response getInventory() {
        log.info("GET /store/inventory");
        return given().spec(SpecBuilder.getDefaultSpec())
                .when().get(ApiEndpoints.STORE_INVENTORY)
                .then().log().all().extract().response();
    }

    public Response placeOrder(StoreOrderRequest order) {
        log.info("POST /store/order - petId: {}", order.getPetId());
        return given().spec(SpecBuilder.getDefaultSpec()).body(order)
                .when().post(ApiEndpoints.STORE_ORDER)
                .then().log().all().extract().response();
    }

    public Response getOrderById(long orderId) {
        log.info("GET /store/order/{}", orderId);
        return given().spec(SpecBuilder.getNoAuthSpec())
                .pathParam("orderId", orderId)
                .when().get(ApiEndpoints.STORE_ORDER_BY_ID)
                .then().log().all().extract().response();
    }

    public Response getOrderByStringId(String orderId) {
        return given().spec(SpecBuilder.getNoAuthSpec())
                .when().get("/store/order/" + orderId)
                .then().log().all().extract().response();
    }

    public Response deleteOrder(long orderId) {
        log.info("DELETE /store/order/{}", orderId);
        return given().spec(SpecBuilder.getDefaultSpec())
                .pathParam("orderId", orderId)
                .when().delete(ApiEndpoints.STORE_ORDER_BY_ID)
                .then().log().all().extract().response();
    }
}
