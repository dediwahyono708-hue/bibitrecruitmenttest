@android
Feature: Simple android smoke

  Scenario: Launch demo app
    Given I launch the demo android app
    Then the app session should be active
