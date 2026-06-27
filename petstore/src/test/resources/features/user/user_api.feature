@user @regression
Feature: User API - User Management

  @smoke
  Scenario: Create a new user
    Given I have a valid user payload
    When I send a POST request to create the user
    Then the response status code should be 200
    And the response field "message" should not be null

  @smoke
  Scenario: Login with valid credentials
    Given I have a valid petstore user
    When I send a GET request to login with the user credentials
    Then the response status code should be 200
    And the login response should contain a session token

  Scenario: Get user by username
    Given I have a valid petstore user
    When I send a GET request to fetch the user by username
    Then the response status code should be 200
    And the response field "username" should not be null

  Scenario: Update user details
    Given I have a valid petstore user
    When I update the user first name to "UpdatedName"
    Then the response status code should be 200

  Scenario: Delete a user
    Given I have a valid petstore user
    When I send a DELETE request for the user
    Then the response status code should be 200

  Scenario: Create users with array
    Given I have a list of 3 valid user payloads
    When I send a POST request to create users with array
    Then the response status code should be 200

  Scenario: Create users with list
    Given I have a list of 3 valid user payloads
    When I send a POST request to create users with list
    Then the response status code should be 200

  Scenario: User logout
    When I send a GET request to logout
    Then the response status code should be 200

  @negative
  Scenario: Get non-existent user returns 404
    When I send a GET request to login with username "nonexistent_xyz_999" and password "wrong"
    Then the response status code should be 200

  @negative
  Scenario: Login with invalid credentials
    When I send a GET request to login with username "" and password ""
    Then the response status code should be 400
