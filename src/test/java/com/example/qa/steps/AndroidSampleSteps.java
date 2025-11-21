package com.example.qa.steps;

import com.example.qa.driver.AppiumDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class AndroidSampleSteps {
    private AppiumDriver driver;

    @Given("I launch the demo android app")
    public void i_launch_the_demo_android_app() throws Exception {
        driver = AppiumDriverFactory.getDriver();
    }

    @Then("the app session should be active")
    public void the_app_session_should_be_active() throws Exception {
        if (driver == null) driver = AppiumDriverFactory.getDriver();
        Assert.assertNotNull("Appium session should be active", driver.getSessionId());
    }
}
