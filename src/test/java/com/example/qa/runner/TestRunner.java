package com.example.qa.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "summary"},
    features = "src/test/resources/features",
    glue = {"com.example.qa.steps"}
)
public class TestRunner {
    static {
        // Diagnostic: print any cucumber-related system properties so command-line tag passing can be validated
        try {
            String co = System.getProperty("cucumber.options");
            String cft = System.getProperty("cucumber.filter.tags");
            System.out.println("[DEBUG] cucumber.options=" + (co==null?"<null>":co));
            System.out.println("[DEBUG] cucumber.filter.tags=" + (cft==null?"<null>":cft));
        } catch (Exception ignored) {}
    }
}
