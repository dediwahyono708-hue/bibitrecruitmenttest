package com.example.qa.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import com.example.qa.selectors.LoginSelectors;

public class LoginPage {
    private final AppiumDriver driver;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
    }

    private WebElement waitFor(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private void openLoginScreenIfNeeded() {
        try {
            // if username field exists, assume already on login screen
            waitFor(LoginSelectors.USERNAME, 2);
            return;
        } catch (Exception ignored) {
        }

        // open menu -> Login Menu Item
        try {
            WebElement menu = waitFor(LoginSelectors.MENU, 5);
            menu.click();
            WebElement loginMenu = waitFor(LoginSelectors.LOGIN_MENU, 5);
            loginMenu.click();
            // now wait for username field
            waitFor(LoginSelectors.USERNAME, 5);
        } catch (Exception e) {
            // ignore, caller will surface if still not present
        }
    }

    public void enterUsername(String username) {
        openLoginScreenIfNeeded();
        WebElement name = waitFor(LoginSelectors.USERNAME, 8);
        name.clear();
        name.sendKeys(username);
    }

    public void enterPassword(String password) {
        openLoginScreenIfNeeded();
        WebElement pass = waitFor(LoginSelectors.PASSWORD, 8);
        pass.clear();
        pass.sendKeys(password);
    }

    public void tapLogin() {
        openLoginScreenIfNeeded();
        WebElement loginBtn = waitFor(LoginSelectors.LOGIN_BTN, 8);
        loginBtn.click();
    }

    public boolean isLoggedIn() {
        try {
            // open menu and check for logout menu item
            WebElement menu = waitFor(LoginSelectors.MENU, 5);
            menu.click();
            waitFor(LoginSelectors.LOGOUT_MENU, 5);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
