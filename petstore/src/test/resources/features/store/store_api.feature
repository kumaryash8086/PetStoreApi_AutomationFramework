@store @regression
Feature: Store API - Order Management

  Background:
    Given a pet exists in the petstore

  @smoke
  Scenario: Get store inventory
    When I send a GET request to fetch the store inventory
    Then the response status code should be 200
    And the inventory response should contain pet status counts
    And the response time should be within acceptable limits

  @smoke
  Scenario: Place a new order
    When I place an order for the pet with quantity 2
    Then the response status code should be 200
    And the response field "id" should not be null
    And the response field "status" should equal "placed"

  Scenario: Get order by ID
    Given an order exists for the pet
    When I send a GET request to fetch the order by ID
    Then the response status code should be 200
    And the response field "petId" should not be null

  Scenario: Delete an order
    Given an order exists for the pet
    When I send a DELETE request for the order
    Then the response status code should be 200

  Scenario: Place order with approved status
    When I place an order for the pet with status "approved"
    Then the response status code should be 200
    And the response field "status" should equal "approved"

  @negative
  Scenario: Get order with invalid ID
    When I send a DELETE request for order ID 99999
    Then the response status code should be 404
