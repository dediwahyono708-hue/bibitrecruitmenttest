package com.example.qa.helpers;

import com.example.qa.driver.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BibitSessionHelper {

    public static void injectLocalStorage(
            String accessToken,
            String refreshToken,
            String acExpire,
            String rfExpire
    ) {
        WebDriver driver = DriverFactory.getDriver();

        driver.get("https://app.bibit.id");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.localStorage.setItem('actoken', arguments[0]);", accessToken);
        js.executeScript("window.localStorage.setItem('hasLoggedIn', 'true');");
        js.executeScript("window.localStorage.setItem('onUserLoggedIn', 'true');");

        js.executeScript("window.localStorage.setItem('rftoken', arguments[0]);", refreshToken);
        js.executeScript("window.localStorage.setItem('acexpire', arguments[0]);", acExpire);
        js.executeScript("window.localStorage.setItem('rfexpire', arguments[0]);", rfExpire);

        driver.navigate().refresh();
    }
}
