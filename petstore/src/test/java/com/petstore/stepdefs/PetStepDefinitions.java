package com.petstore.stepdefs;

import com.petstore.api.PetAPI;
import com.petstore.models.request.PetRequest;
import com.petstore.utils.ResponseValidator;
import com.petstore.utils.ScenarioContext;
import com.petstore.utils.TestDataFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.util.List;

public class PetStepDefinitions {

    private static final Logger log = LoggerFactory.getLogger(PetStepDefinitions.class);
    private final ScenarioContext ctx;
    private final PetAPI petAPI = new PetAPI();

    public PetStepDefinitions(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("I have a valid pet payload with status {string}")
    public void buildPetPayload(String status) {
        PetRequest pet;
        if ("pending".equals(status)) {
            pet = TestDataFactory.pendingPet();
        } else if ("sold".equals(status)) {
            pet = TestDataFactory.soldPet();
        } else {
            pet = TestDataFactory.availablePet();
        }
        ctx.set("PET_REQUEST", pet);
        ctx.set(ScenarioContext.PET_STATUS, status);
        log.info("Pet payload built: name={}, status={}", pet.getName(), pet.getStatus());
    }

    @Given("I have a pet payload without name")
    public void buildPetWithoutName() {
        ctx.set("PET_REQUEST", TestDataFactory.petWithoutName());
    }

    @Given("a pet exists in the petstore")
    public void createPetAndSaveId() {
        PetRequest pet = TestDataFactory.availablePet();
        Response response = petAPI.addPet(pet);
        ResponseValidator.assertStatusCode(response, 200);
        long petId = response.jsonPath().getLong("id");
        ctx.set(ScenarioContext.PET_ID, petId);
        ctx.set(ScenarioContext.PET_NAME, response.jsonPath().getString("name"));
        log.info("Pet created with id: {}", petId);
    }

    @Given("a pet exists in the petstore with tag {string}")
    public void createPetWithTag(String tag) {
        Response response = petAPI.addPet(TestDataFactory.petWithTags(tag));
        ResponseValidator.assertStatusCode(response, 200);
        ctx.set(ScenarioContext.PET_ID, response.jsonPath().getLong("id"));
    }

    @When("I send a POST request to add the pet")
    public void sendAddPet() {
        ctx.setResponse(petAPI.addPet((PetRequest) ctx.get("PET_REQUEST")));
    }

    @When("I send a PUT request to update the pet")
    public void sendUpdatePet() {
        ctx.setResponse(petAPI.updatePet((PetRequest) ctx.get("PET_REQUEST")));
    }

    @When("I update the pet name to {string} and status to {string}")
    public void updatePetNameAndStatus(String name, String status) {
        PetRequest updated = TestDataFactory.availablePet();
        updated.setId(ctx.getLong(ScenarioContext.PET_ID));
        updated.setName(name);
        updated.setStatus(status);
        ctx.setResponse(petAPI.updatePet(updated));
    }

    @When("I send a GET request to find pets by status {string}")
    public void findPetsByStatus(String status) {
        ctx.setResponse(petAPI.findPetsByStatus(status));
    }

    @When("I send a GET request to find pets by tag {string}")
    public void findPetsByTag(String tag) {
        ctx.setResponse(petAPI.findPetsByTags(tag));
    }

    @When("I send a GET request to fetch the pet by ID")
    public void getPetById() {
        ctx.setResponse(petAPI.getPetById(ctx.getLong(ScenarioContext.PET_ID)));
    }

    @When("I send a GET request for pet ID {long}")
    public void getPetByLiteralId(long id) {
        ctx.setResponse(petAPI.getPetById(id));
    }

    @When("I send a GET request for invalid pet ID {string}")
    public void getPetByInvalidId(String id) {
        ctx.setResponse(petAPI.getPetByStringId(id));
    }

    @When("I update the pet using form data with name {string} and status {string}")
    public void updatePetWithForm(String name, String status) {
        ctx.setResponse(petAPI.updatePetWithForm(ctx.getLong(ScenarioContext.PET_ID), name, status));
    }

    @When("I send a DELETE request for the pet")
    public void deletePet() {
        ctx.setResponse(petAPI.deletePet(ctx.getLong(ScenarioContext.PET_ID)));
    }

    @When("I send a DELETE request for pet ID {long}")
    public void deletePetById(long id) {
        ctx.setResponse(petAPI.deletePet(id));
    }

    @Then("the response status code should be {int}")
    public void assertStatusCode(int expected) {
        ResponseValidator.assertStatusCode(ctx.getResponse(), expected);
    }

    @Then("the response field {string} should not be null")
    public void assertFieldNotNull(String path) {
        ResponseValidator.assertFieldNotNull(ctx.getResponse(), path);
    }

    @Then("the response field {string} should equal {string}")
    public void assertFieldEquals(String path, String expected) {
        ResponseValidator.assertFieldEquals(ctx.getResponse(), path, expected);
    }

    @Then("the response should contain a non-empty list of pets")
    public void assertNonEmptyPetList() {
        List<?> pets = ctx.getResponse().jsonPath().getList("$");
        Assert.assertNotNull(pets, "Pet list is null");
        Assert.assertFalse(pets.isEmpty(), "Pet list is empty");
    }

    @Then("all returned pets should have status {string}")
    public void assertAllPetsStatus(String expectedStatus) {
        List<String> statuses = ctx.getResponse().jsonPath().getList("status");
        for (String s : statuses) {
            Assert.assertTrue(s.equalsIgnoreCase(expectedStatus),
                    "Expected status '" + expectedStatus + "' but found '" + s + "'");
        }
    }

    @Then("the response time should be within acceptable limits")
    public void assertResponseTime() {
        ResponseValidator.assertResponseTime(ctx.getResponse());
        ResponseValidator.logResponse(ctx.getResponse());
    }
}
