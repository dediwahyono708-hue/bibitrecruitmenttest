package com.example.qa.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import com.example.qa.selectors.CheckoutSelectors;

public class CheckoutPage {
    private final AppiumDriver driver;

    public CheckoutPage(AppiumDriver driver) {
        this.driver = driver;
    }

    private WebElement waitFor(By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void fillCustomerInfo(String fullName, String address1, String address2, String city, String state, String zip, String country) {
        waitFor(CheckoutSelectors.FULLNAME, 5).sendKeys(fullName);
        WebElement a1 = waitFor(CheckoutSelectors.ADDRESS1, 5);
        a1.click();
        a1.sendKeys(address1);
        waitFor(CheckoutSelectors.ADDRESS2, 5).sendKeys(address2);
        waitFor(CheckoutSelectors.CITY, 5).sendKeys(city);
        WebElement s = waitFor(CheckoutSelectors.STATE, 5);
        s.click();
        s.sendKeys(state);
        waitFor(CheckoutSelectors.ZIP, 5).sendKeys(zip);
        waitFor(CheckoutSelectors.COUNTRY, 5).sendKeys(country);
        waitFor(CheckoutSelectors.SAVE_INFO, 5).click();
    }

    public void fillPayment(String cardName, String cardNumber, String expiry, String cvv) {
        waitFor(CheckoutSelectors.PAYMENT_NAME, 5).sendKeys(cardName);
        waitFor(CheckoutSelectors.PAYMENT_CARD, 5).sendKeys(cardNumber);
        waitFor(CheckoutSelectors.PAYMENT_EXPIRY, 5).sendKeys(expiry);
        waitFor(CheckoutSelectors.PAYMENT_CVV, 5).sendKeys(cvv);
        waitFor(CheckoutSelectors.SAVE_PAYMENT, 5).click();
    }

    public void placeOrder() {
        waitFor(CheckoutSelectors.PLACE_ORDER, 5).click();
    }

    public boolean verifyOrderSuccess() {
        try {
            waitFor(CheckoutSelectors.ORDER_SUCCESS, 8);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
