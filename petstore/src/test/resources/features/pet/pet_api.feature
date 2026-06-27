@pet @regression
Feature: Pet API - CRUD Operations

  Background:
    Given a pet exists in the petstore

  @smoke
  Scenario: Add a new available pet
    Given I have a valid pet payload with status "available"
    When I send a POST request to add the pet
    Then the response status code should be 200
    And the response field "id" should not be null
    And the response field "status" should equal "available"
    And the response time should be within acceptable limits

  @smoke
  Scenario: Get pet by valid ID
    When I send a GET request to fetch the pet by ID
    Then the response status code should be 200
    And the response field "id" should not be null
    And the response field "name" should not be null

  Scenario: Update an existing pet
    When I update the pet name to "UpdatedDog" and status to "sold"
    Then the response status code should be 200
    And the response field "name" should equal "UpdatedDog"
    And the response field "status" should equal "sold"

  @smoke
  Scenario: Find pets by status available
    When I send a GET request to find pets by status "available"
    Then the response status code should be 200
    And the response should contain a non-empty list of pets
    And all returned pets should have status "available"

  Scenario: Find pets by status pending
    When I send a GET request to find pets by status "pending"
    Then the response status code should be 200

  Scenario: Find pets by status sold
    When I send a GET request to find pets by status "sold"
    Then the response status code should be 200

  Scenario: Delete an existing pet
    When I send a DELETE request for the pet
    Then the response status code should be 200

  @negative
  Scenario: Get pet with non-existent ID returns 404
    When I send a GET request for pet ID 999999999
    Then the response status code should be 404

  @negative
  Scenario: Get pet with invalid string ID
    When I send a GET request for invalid pet ID "abc-invalid"
    Then the response status code should be 404

  Scenario: Update pet status using form data
    When I update the pet using form data with name "FormUpdated" and status "pending"
    Then the response status code should be 200

  Scenario: Add a pending pet
    Given I have a valid pet payload with status "pending"
    When I send a POST request to add the pet
    Then the response status code should be 200
    And the response field "status" should equal "pending"

  Scenario: Add a sold pet
    Given I have a valid pet payload with status "sold"
    When I send a POST request to add the pet
    Then the response status code should be 200
    And the response field "status" should equal "sold"
