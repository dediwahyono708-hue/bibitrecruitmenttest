package com.example.qa.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            // Simple ChromeDriver example. In real projects use WebDriverManager or a proper driver manager.
            ChromeOptions opts = new ChromeOptions();
            // Allow toggling headless mode via system property `browser.headless` (default: true)
            boolean headless = Boolean.parseBoolean(System.getProperty("browser.headless", "true"));
            if (headless) {
                // modern headless flag; fall back to --headless if needed on older Chrome
                opts.addArguments("--headless=new");
            } else {
                // started headed: maximize or set window size for consistency
                opts.addArguments("--start-maximized");
            }
            WebDriver wd = new ChromeDriver(opts);
            driver.set(wd);
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
