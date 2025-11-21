package com.example.qa.steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import com.example.qa.utils.APIClient;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class PostSteps {

    Response response;
    String baseUrl = "https://jsonplaceholder.typicode.com";

    @Given("I prepare a new post request")
    public void prepare_new_post() {
        APIClient.setBody(
            "{\n" +
            "  \"title\": \"Learn API Testing\",\n" +
            "  \"body\": \"Practicing API testing with JSONPlaceholder\",\n" +
            "  \"userId\": 101\n" +
            "}"
        );

    }

    @When("I send POST request to create post")
    public void create_post() {
        response = APIClient.post(baseUrl + "/posts");
    }

    @Then("The response should contain the correct created post data")
    public void verify_created_post() {
        response.then().statusCode(201);

        assertThat(response.jsonPath().getString("title"), equalTo("Learn API Testing"));
        assertThat(response.jsonPath().getString("body"), equalTo("Practicing API testing with JSONPlaceholder"));
        assertThat(response.jsonPath().getInt("userId"), equalTo(101));
    }

    @When("I send GET request to retrieve posts")
    public void get_posts() {
        response = APIClient.get(baseUrl + "/posts");
    }

    @Then("All posts should have non-null id")
    public void verify_all_posts() {
        response.then().statusCode(200);

        var list = response.jsonPath().getList("id");

        for (Object id : list) {
            assertThat(id, notNullValue());
        }
    }

    @When("I send DELETE request to delete post with id 1")
    public void delete_post() {
        response = APIClient.delete(baseUrl + "/posts/1");
    }

    @Then("The delete response code should be 200")
    public void verify_delete() {
        response.then().statusCode(200);
    }

    @Given("I prepare an update post request")
    public void update_post_body() {
        APIClient.setBody(
                "{\n" +
                 " \"title\": \"Updated Post Title\",\n" +
                 " \"body\": \"This is the updated body content.\",\n" +
                 " \"userId\": 99\n" + 
                "}\n"
                );
    }

    @When("I send PUT request to update post with id 1")
    public void update_post() {
        response = APIClient.put(baseUrl + "/posts/1");
    }

    @Then("The response should contain the updated post data")
    public void verify_updated_post() {
        response.then().statusCode(200);

        assertThat(response.jsonPath().getString("title"), equalTo("Updated Post Title"));
        assertThat(response.jsonPath().getString("body"), equalTo("This is the updated body content."));
        assertThat(response.jsonPath().getInt("userId"), equalTo(99));
    }

    @Then("The response should match {string}")
    public void validate_schema(String schemaName) {
        File schema = new File("src/test/resources/schema/" + schemaName);
        response.then().assertThat().body(matchesJsonSchema(schema));
    }
}
