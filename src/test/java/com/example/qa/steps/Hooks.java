package com.example.qa.steps;

import com.example.qa.driver.DriverFactory;
import com.example.qa.driver.AppiumDriverFactory;
import com.example.qa.context.TestContext;
import io.cucumber.java.After;

public class Hooks {
    @After
    public void afterScenario() {
        // Quit web driver if it was created
        try {
            DriverFactory.quitDriver();
        } catch (Exception ignored) {
        }
        // Quit Appium driver if it was created
        try {
            AppiumDriverFactory.quitDriver();
        } catch (Exception ignored) {
        }
        // Clear any per-thread test context
        try { TestContext.clear(); } catch (Exception ignored) {}
    }
}
