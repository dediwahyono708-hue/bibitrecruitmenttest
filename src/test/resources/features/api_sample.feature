@api
Feature: API Testing for JSONPlaceholder

  Scenario: Create a new post
    Given I prepare a new post request
    When I send POST request to create post
    Then The response should contain the correct created post data
    And The response should match "create_post_schema.json"

  Scenario: Retrieve all posts
    When I send GET request to retrieve posts
    Then All posts should have non-null id
    And The response should match "get_posts_schema.json"

  Scenario: Delete a post
    When I send DELETE request to delete post with id 1
    Then The delete response code should be 200
    And The response should match "delete_post_schema.json"

  Scenario: Update a post
    Given I prepare an update post request
    When I send PUT request to update post with id 1
    Then The response should contain the updated post data
    And The response should match "update_post_schema.json"

