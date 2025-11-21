package com.example.qa.steps;

import com.example.qa.helpers.BibitApiHelper;
import com.example.qa.helpers.BibitSessionHelper;
import com.example.qa.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.time.Duration;

public class WebSteps {

    WebDriver driver = DriverFactory.getDriver();

    @Given("the user is logged in to Bibit")
    public void userLoggedIn() {
        driver.get("https://app.bibit.id/login");
        WebElement tel = driver.findElement(By.cssSelector("input[type='tel']"));
        tel.sendKeys("08123456789");
        WebElement btnLogin = driver.findElement(By.cssSelector("button[aria-label='Login button']"));
        btnLogin.click();
        String otp = "123456";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement key1 = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div[data-testid='num-1']")
            )
        );

        for (char digit : otp.toCharArray()) {
            driver.findElement(By.cssSelector("div[data-num='" + digit + "']")).click();
        }


        var login = BibitApiHelper.login("08123456789", "123456");

        BibitSessionHelper.injectLocalStorage(
                login.accessToken,
                login.refreshToken,
                login.acExpire,
                login.rfExpire
        );
    }

    @When("the user navigates to the Explore page")
    public void goToExplore() {
        driver.get("https://app.bibit.id/explore");
    }

    @When("the user searches for {string}")
    public void searchForProduct(String keyword) {
        driver.findElement(By.cssSelector("input[data-testid='searchInput']"))
              .sendKeys(keyword);
    }

    @Then("search results related to {string} should be displayed")
    public void verifySearchResults(String keyword) {
        String text = driver.findElement(By.cssSelector("[data-testid='searchResultText']")).getText();
        assertTrue(text.contains(keyword));
    }

    @When("the user logs out")
    public void userLogsOut() {
        driver.findElement(By.cssSelector("[data-testid='profileMenu']")).click();
        driver.findElement(By.cssSelector("[data-testid='logoutButton']")).click();
    }

    @Then("the user should be redirected to login page")
    public void verifyLogout() {
        assertTrue(driver.getCurrentUrl().contains("login"));
    }
}

