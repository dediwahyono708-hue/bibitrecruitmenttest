@android
Feature: Android end-to-end flows

  Background:
    Given the app is started and I am on the login screen

  @login
  Scenario: Login success
    When I login with username "demo" and password "password"
    Then I should see the products list

  @buy
  Scenario: Buy Sauce Lab Back Packs blue x2
    When I login with username "demo" and password "password"
    And I add item "Sauce Labs Backpack" with color "Blue" quantity 2 to the cart
    Then the cart should contain 2 items of "Sauce Labs Backpack"
    When I complete checkout with sample data
    Then the order should be successful

  @sort
  Scenario: Sort products by name descending and price ascending
    When I login with username "demo" and password "password"
    And I sort products by name descending
    And I sort products by price ascending
    Then the products list should be sorted by name descending and price ascending
