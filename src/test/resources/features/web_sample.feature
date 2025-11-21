@web
Feature: Bibit Automation Flow

  Scenario: Login, Search Product, and Logout
    Given the user is logged in to Bibit
    When the user navigates to the Explore page
    And the user searches for "Obligasi"
    Then search results related to "Obligasi" should be displayed
    When the user logs out
    Then the user should be redirected to login page
