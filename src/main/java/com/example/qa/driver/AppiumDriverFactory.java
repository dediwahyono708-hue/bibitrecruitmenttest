package com.example.qa.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumDriverFactory {
    /**
     * Create an Android Appium driver with sensible defaults for the QA test.
     * Defaults can be overridden with system properties:
     * -appium.url (default: http://127.0.0.1:4723/)
     * -deviceName
     * -app
     * -appPackage
     * -appActivity
     */
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    private static AppiumDriver createAndroidDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        // Use provided defaults; allow overrides via system properties
        String platformName = System.getProperty("platformName", "Android");
        String automationName = System.getProperty("automationName", "UiAutomator2");
        String deviceName = System.getProperty("deviceName", "Android Emulator");
        String appPath = System.getProperty("app", "/Users/dediwaa/Downloads/mda-2.2.0-25.apk");
        String appPackage = System.getProperty("appPackage", "com.saucelabs.mydemoapp.android");
        String appActivity = System.getProperty("appActivity", "com.saucelabs.mydemoapp.android.view.activities.SplashActivity");

        // Appium prefixed capability names (W3C) — use exact keys from the user's JSON
        caps.setCapability("platformName", platformName);
        caps.setCapability("appium:automationName", automationName);
        caps.setCapability("appium:deviceName", deviceName);
        caps.setCapability("appium:app", appPath);
        caps.setCapability("appium:appPackage", appPackage);
        caps.setCapability("appium:appActivity", appActivity);

        String appiumUrl = System.getProperty("appium.url", "http://127.0.0.1:4723/");

        return new AndroidDriver(new URL(appiumUrl), caps);
    }

    public static AppiumDriver getDriver() throws MalformedURLException {
        if (driver.get() == null) {
            driver.set(createAndroidDriver());
        }
        return driver.get();
    }

    public static void quitDriver() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
            }
        } catch (Exception ignored) {
        }
    }
}
