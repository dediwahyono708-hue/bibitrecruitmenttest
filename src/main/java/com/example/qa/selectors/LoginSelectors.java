package com.example.qa.selectors;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class LoginSelectors {
    public static final By USERNAME = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
    public static final By PASSWORD = AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET");
    public static final By MENU = AppiumBy.accessibilityId("View menu");
    public static final By LOGIN_MENU = AppiumBy.accessibilityId("Login Menu Item");
    public static final By LOGIN_BTN = AppiumBy.accessibilityId("Tap to login with given credentials");
    public static final By LOGOUT_MENU = AppiumBy.accessibilityId("Logout Menu Item");
}
