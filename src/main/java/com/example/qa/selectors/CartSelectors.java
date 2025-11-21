package com.example.qa.selectors;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class CartSelectors {
    public static final By PRODUCT_RV_ITEMS = AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.saucelabs.mydemoapp.android:id/productRV']//android.view.ViewGroup");
    public static final By PRODUCT_TITLE = AppiumBy.id("com.saucelabs.mydemoapp.android:id/titleTV");
    public static final By PRODUCT_QUANTITY = AppiumBy.id("com.saucelabs.mydemoapp.android:id/noTV");
    public static final By DISPLAY_COLOR = AppiumBy.accessibilityId("Displays color of selected product");
    public static final By PROCEED_TO_CHECKOUT = AppiumBy.accessibilityId("Confirms products for checkout");
}
