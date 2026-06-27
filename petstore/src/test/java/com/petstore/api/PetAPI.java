package com.petstore.api;

import com.petstore.config.SpecBuilder;
import com.petstore.constants.ApiEndpoints;
import com.petstore.models.request.PetRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.given;

public class PetAPI {

    private static final Logger log = LoggerFactory.getLogger(PetAPI.class);

    public Response addPet(PetRequest pet) {
        log.info("POST /pet - name: {}", pet.getName());
        return given().spec(SpecBuilder.getDefaultSpec()).body(pet)
                .when().post(ApiEndpoints.PET)
                .then().log().all().extract().response();
    }

    public Response updatePet(PetRequest pet) {
        log.info("PUT /pet - id: {}", pet.getId());
        return given().spec(SpecBuilder.getDefaultSpec()).body(pet)
                .when().put(ApiEndpoints.PET)
                .then().log().all().extract().response();
    }

    public Response findPetsByStatus(String status) {
        log.info("GET /pet/findByStatus - status: {}", status);
        return given().spec(SpecBuilder.getNoAuthSpec())
                .queryParam("status", status)
                .when().get(ApiEndpoints.PET_FIND_BY_STATUS)
                .then().log().all().extract().response();
    }

    public Response findPetsByTags(String... tags) {
        log.info("GET /pet/findByTags - tags: {}", (Object) tags);
        RequestSpecification req = given().spec(SpecBuilder.getNoAuthSpec());
        for (String tag : tags) req = req.queryParam("tags", tag);
        return req.when().get(ApiEndpoints.PET_FIND_BY_TAGS)
                .then().log().all().extract().response();
    }

    public Response getPetById(long petId) {
        log.info("GET /pet/{}", petId);
        return given().spec(SpecBuilder.getNoAuthSpec())
                .pathParam("petId", petId)
                .when().get(ApiEndpoints.PET_BY_ID)
                .then().log().all().extract().response();
    }

    public Response getPetByStringId(String petId) {
        log.info("GET /pet/{} (string id)", petId);
        return given().spec(SpecBuilder.getNoAuthSpec())
                .when().get("/pet/" + petId)
                .then().log().all().extract().response();
    }

    public Response updatePetWithForm(long petId, String name, String status) {
        log.info("POST /pet/{} (form) - name:{}, status:{}", petId, name, status);
        return given().spec(SpecBuilder.getDefaultSpec())
                .contentType("application/x-www-form-urlencoded")
                .pathParam("petId", petId)
                .formParam("name", name)
                .formParam("status", status)
                .when().post(ApiEndpoints.PET_BY_ID)
                .then().log().all().extract().response();
    }

    public Response deletePet(long petId) {
        log.info("DELETE /pet/{}", petId);
        return given().spec(SpecBuilder.getDefaultSpec())
                .pathParam("petId", petId)
                .when().delete(ApiEndpoints.PET_BY_ID)
                .then().log().all().extract().response();
    }
}
