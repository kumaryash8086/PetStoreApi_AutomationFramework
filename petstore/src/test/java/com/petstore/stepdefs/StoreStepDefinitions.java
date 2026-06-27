package com.petstore.stepdefs;

import com.petstore.api.StoreAPI;
import com.petstore.models.request.StoreOrderRequest;
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
import java.util.Map;

public class StoreStepDefinitions {

    private static final Logger log = LoggerFactory.getLogger(StoreStepDefinitions.class);
    private final ScenarioContext ctx;
    private final StoreAPI storeAPI = new StoreAPI();

    public StoreStepDefinitions(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("an order exists for the pet")
    public void createOrderForPet() {
        Long petId = ctx.getLong(ScenarioContext.PET_ID);
        long usePetId = (petId != null) ? petId : 1L;
        Response response = storeAPI.placeOrder(TestDataFactory.validOrder(usePetId));
        ResponseValidator.assertStatusCode(response, 200);
        ctx.set(ScenarioContext.ORDER_ID, response.jsonPath().getLong("id"));
    }

    @When("I send a GET request to fetch the store inventory")
    public void getInventory() {
        ctx.setResponse(storeAPI.getInventory());
    }

    @When("I place an order for the pet with quantity {int}")
    public void placeOrderWithQty(int qty) {
        Long petId = ctx.getLong(ScenarioContext.PET_ID);
        long usePetId = (petId != null) ? petId : 1L;
        StoreOrderRequest order = TestDataFactory.validOrder(usePetId);
        order.setQuantity(qty);
        ctx.setResponse(storeAPI.placeOrder(order));
    }

    @When("I place an order for the pet with status {string}")
    public void placeOrderWithStatus(String status) {
        Long petId = ctx.getLong(ScenarioContext.PET_ID);
        long usePetId = (petId != null) ? petId : 1L;
        StoreOrderRequest order = TestDataFactory.validOrder(usePetId);
        order.setStatus(status);
        ctx.setResponse(storeAPI.placeOrder(order));
    }

    @When("I send a GET request to fetch the order by ID")
    public void getOrderById() {
        ctx.setResponse(storeAPI.getOrderById(ctx.getLong(ScenarioContext.ORDER_ID)));
    }

    @When("I send a DELETE request for the order")
    public void deleteOrder() {
        ctx.setResponse(storeAPI.deleteOrder(ctx.getLong(ScenarioContext.ORDER_ID)));
    }

    @Then("the inventory response should contain pet status counts")
    public void assertInventory() {
        Map<String, ?> inventory = ctx.getResponse().jsonPath().getMap("$");
        Assert.assertNotNull(inventory, "Inventory should not be null");
        Assert.assertFalse(inventory.isEmpty(), "Inventory should not be empty");
        log.info("Inventory: {}", inventory);
    }
}
