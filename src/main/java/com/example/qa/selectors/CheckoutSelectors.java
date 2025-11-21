package com.example.qa.selectors;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class CheckoutSelectors {
    public static final By FULLNAME = AppiumBy.id("com.saucelabs.mydemoapp.android:id/fullNameET");
    public static final By ADDRESS1 = AppiumBy.id("com.saucelabs.mydemoapp.android:id/address1ET");
    public static final By ADDRESS2 = AppiumBy.id("com.saucelabs.mydemoapp.android:id/address2ET");
    public static final By CITY = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cityET");
    public static final By STATE = AppiumBy.id("com.saucelabs.mydemoapp.android:id/stateET");
    public static final By ZIP = AppiumBy.id("com.saucelabs.mydemoapp.android:id/zipET");
    public static final By COUNTRY = AppiumBy.id("com.saucelabs.mydemoapp.android:id/countryET");
    public static final By SAVE_INFO = AppiumBy.accessibilityId("Saves user info for checkout");

    public static final By PAYMENT_NAME = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
    public static final By PAYMENT_CARD = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cardNumberET");
    public static final By PAYMENT_EXPIRY = AppiumBy.id("com.saucelabs.mydemoapp.android:id/expirationDateET");
    public static final By PAYMENT_CVV = AppiumBy.id("com.saucelabs.mydemoapp.android:id/securityCodeET");
    public static final By SAVE_PAYMENT = AppiumBy.accessibilityId("Saves payment info and launches screen to review checkout data");

    public static final By PLACE_ORDER = AppiumBy.accessibilityId("Completes the process of checkout");
    public static final By ORDER_SUCCESS = AppiumBy.id("com.saucelabs.mydemoapp.android:id/completeTV");
}
