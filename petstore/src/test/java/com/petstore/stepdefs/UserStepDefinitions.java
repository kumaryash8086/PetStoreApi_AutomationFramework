package com.petstore.stepdefs;

import com.petstore.api.UserAPI;
import com.petstore.models.request.UserRequest;
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

public class UserStepDefinitions {

    private static final Logger log = LoggerFactory.getLogger(UserStepDefinitions.class);
    private final ScenarioContext ctx;
    private final UserAPI userAPI = new UserAPI();

    public UserStepDefinitions(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("I have a valid user payload")
    public void buildValidUser() {
        UserRequest user = TestDataFactory.validUser();
        ctx.set("USER_REQUEST", user);
        ctx.set(ScenarioContext.USERNAME, user.getUsername());
        ctx.set(ScenarioContext.PASSWORD, user.getPassword());
    }

    @Given("I have a valid petstore user")
    public void createAndSaveUser() {
        UserRequest user = TestDataFactory.validUser();
        Response response = userAPI.createUser(user);
        ResponseValidator.assertStatusCode(response, 200);
        ctx.set(ScenarioContext.USERNAME, user.getUsername());
        ctx.set(ScenarioContext.PASSWORD, user.getPassword());
        ctx.set("USER_REQUEST", user);
        log.info("User created: {}", user.getUsername());
    }

    @Given("I have a list of {int} valid user payloads")
    public void buildUserList(int count) {
        ctx.set("USER_LIST", TestDataFactory.listOfUsers(count));
    }

    @When("I send a POST request to create the user")
    public void createUser() {
        ctx.setResponse(userAPI.createUser((UserRequest) ctx.get("USER_REQUEST")));
    }

    @When("I send a POST request to create users with array")
    public void createUsersWithArray() {
        ctx.setResponse(userAPI.createUsersWithArray((List<UserRequest>) ctx.get("USER_LIST")));
    }

    @When("I send a POST request to create users with list")
    public void createUsersWithList() {
        ctx.setResponse(userAPI.createUsersWithList((List<UserRequest>) ctx.get("USER_LIST")));
    }

    @When("I send a GET request to login with the user credentials")
    public void loginWithSavedCredentials() {
        ctx.setResponse(userAPI.loginUser(
                ctx.getString(ScenarioContext.USERNAME),
                ctx.getString(ScenarioContext.PASSWORD)));
    }

    @When("I send a GET request to login with username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        ctx.setResponse(userAPI.loginUser(username, password));
    }

    @When("I send a GET request to logout")
    public void logout() {
        ctx.setResponse(userAPI.logoutUser());
    }

    @When("I send a GET request to fetch the user by username")
    public void getUserByUsername() {
        ctx.setResponse(userAPI.getUserByName(ctx.getString(ScenarioContext.USERNAME)));
    }

    @When("I update the user first name to {string}")
    public void updateUser(String firstName) {
        UserRequest user = ctx.get("USER_REQUEST");
        user.setFirstName(firstName);
        ctx.setResponse(userAPI.updateUser(ctx.getString(ScenarioContext.USERNAME), user));
    }

    @When("I send a DELETE request for the user")
    public void deleteUser() {
        ctx.setResponse(userAPI.deleteUser(ctx.getString(ScenarioContext.USERNAME)));
    }

    @Then("the login response should contain a session token")
    public void assertSessionToken() {
        String message = ctx.getResponse().jsonPath().getString("message");
        Assert.assertNotNull(message, "Login session token should not be null");
        log.info("Session token: {}", message);
    }
}
