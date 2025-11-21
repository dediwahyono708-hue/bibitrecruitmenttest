package com.example.qa.selectors;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ProductsSelectors {
    public static final By PRODUCT_IMAGE_FIRST = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\").instance(0)");
    public static final By COLORS_RECYCLER = AppiumBy.accessibilityId("Displays available colors of selected product");
    public static final By COLOR_SELECTION_INDICATOR = AppiumBy.accessibilityId("Indicates when color is selected");
    public static final By INCREASE_QTY = AppiumBy.accessibilityId("Increase item quantity");
    public static final By QUANTITY_TEXT = AppiumBy.id("com.saucelabs.mydemoapp.android:id/noTV");
    public static final By ADD_TO_CART = AppiumBy.accessibilityId("Tap to add product to cart");
    public static final By CART_ICON = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(3)");
    public static final By DISPLAY_COLOR = AppiumBy.accessibilityId("Displays color of selected product");
    public static final By CONFIRM_CHECKOUT = AppiumBy.accessibilityId("Confirms products for checkout");
    public static final By SORT_MENU = AppiumBy.accessibilityId("Shows current sorting order and displays available sorting options");
}
