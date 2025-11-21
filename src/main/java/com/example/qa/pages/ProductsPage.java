package com.example.qa.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import com.example.qa.selectors.ProductsSelectors;
import com.example.qa.context.TestContext;

public class ProductsPage {
    private final AppiumDriver driver;

    public ProductsPage(AppiumDriver driver) {
        this.driver = driver;
    }

    private WebElement waitFor(By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean isOnProductsList() {
        try {
            return driver.findElement(AppiumBy.accessibilityId("View menu")).isDisplayed();
        } catch (Exception e) {
            try {
                return driver.findElement(AppiumBy.accessibilityId("test-Cart")).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    // open first product by resource id instance(0)
    public void openFirstProduct() {
        WebElement el = waitFor(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\").instance(0)"), 8);
        el.click();
    }

    public void selectColor(String colorAccessibility) {
        // e.g. "Blue color"
        String desc = colorAccessibility;
        // Preferred approach: find the RecyclerView that contains colors, then locate the child ViewGroup
        try {
            WebElement colorList = waitFor(ProductsSelectors.COLORS_RECYCLER, 5);
            // find child viewgroups under the recycler view
            java.util.List<WebElement> items = colorList.findElements(AppiumBy.className("android.view.ViewGroup"));
            for (WebElement item : items) {
                try {
                    // inside this item, look for the color image with exact content-desc
                    WebElement colorImg = item.findElement(AppiumBy.accessibilityId(desc));
                    // click the color image
                    colorImg.click();
                    // after clicking, wait for the selection indicator inside the same item
                    try {
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                                .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfNestedElementLocatedBy(item, ProductsSelectors.COLOR_SELECTION_INDICATOR));
                        // store selected color in TestContext for later verification in cart
                        try { TestContext.setSelectedColor(desc); } catch (Exception ignored) {}
                        return;
                    } catch (Exception ignored) {
                        // if indicator not present yet, continue to next item or fallback
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }

        // Fallbacks if structured search fails: try UIAutomator description selector first
        try {
            WebElement el = waitFor(AppiumBy.androidUIAutomator("new UiSelector().description(\"" + desc + "\")"), 4);
            el.click();
            try { TestContext.setSelectedColor(desc); } catch (Exception ignored) {}
            return;
        } catch (Exception ignored) {
        }

        // accessibility id fallback
        try {
            WebElement el = waitFor(AppiumBy.accessibilityId(colorAccessibility), 4);
            el.click();
            try { TestContext.setSelectedColor(colorAccessibility); } catch (Exception ignored) {}
            return;
        } catch (Exception ignored) {
        }

        // contains-text fallback
        try {
            WebElement el = waitFor(AppiumBy.xpath("//*[contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + colorAccessibility.toLowerCase() + "') or contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + colorAccessibility.toLowerCase() + "') ]"), 4);
            el.click();
            try { TestContext.setSelectedColor(colorAccessibility); } catch (Exception ignored) {}
        } catch (Exception e) {
            throw new RuntimeException("Could not find color selector for: " + colorAccessibility, e);
        }
    }

    public void increaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            WebElement inc = waitFor(ProductsSelectors.INCREASE_QTY, 5);
            inc.click();
        }
    }

    public int readQuantityFromDetails() {
        WebElement qty = waitFor(ProductsSelectors.QUANTITY_TEXT, 5);
        try { return Integer.parseInt(qty.getText().replaceAll("[^0-9]","")); } catch (Exception e) { return -1; }
    }

    public void tapAddToCartFromDetails() {
        WebElement add = waitFor(ProductsSelectors.ADD_TO_CART, 5);
        add.click();
    }

    public void openCartIcon() {
        // provided selector: className instance(3)
        WebElement el = waitFor(ProductsSelectors.CART_ICON, 5);
        el.click();
    }

    public void openSelectedProductInCart() {
        // click the displayed color element to view selected product details
        WebElement el = waitFor(ProductsSelectors.DISPLAY_COLOR, 5);
        el.click();
    }

    public void confirmProductsForCheckout() {
        WebElement el = waitFor(ProductsSelectors.CONFIRM_CHECKOUT, 5);
        el.click();
    }

    // Sorting helpers (best-effort locators)
    public void sortByNameDescending() {
        try {
            // open sort menu using provided accessibility id
            waitFor(ProductsSelectors.SORT_MENU, 5).click();
            // select "Name - Descending"
            waitFor(AppiumBy.androidUIAutomator("new UiSelector().text(\"Name - Descending\")"), 5).click();
        } catch (Exception ignored) {
        }
    }

    public void sortByPriceAscending() {
        try {
            // open sort menu
            waitFor(ProductsSelectors.SORT_MENU, 5).click();
            // select "Price - Ascending"
            waitFor(AppiumBy.androidUIAutomator("new UiSelector().text(\"Price - Ascending\")"), 5).click();
        } catch (Exception ignored) {
        }
    }

    public boolean verifyNameDescending() {
        try {
            List<WebElement> els = driver.findElements(AppiumBy.xpath("//android.widget.TextView[contains(@resource-id,'name') or contains(@content-desc,'test-Item title')]") );
            java.util.List<String> names = new java.util.ArrayList<>();
            for (WebElement e : els) names.add(e.getText());
            java.util.List<String> sorted = new java.util.ArrayList<>(names);
            sorted.sort(java.util.Collections.reverseOrder());
            return names.equals(sorted);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyPriceAscending() {
        try {
            List<WebElement> els = driver.findElements(AppiumBy.xpath("//android.widget.TextView[contains(@resource-id,'price')]") );
            java.util.List<Double> prices = new java.util.ArrayList<>();
            for (WebElement e : els) {
                try {
                    String text = e.getText().replaceAll("[^0-9.,]", "").replace(',', '.');
                    prices.add(Double.parseDouble(text));
                } catch (Exception ignored) {}
            }
            java.util.List<Double> sorted = new java.util.ArrayList<>(prices);
            sorted.sort(java.util.Comparator.naturalOrder());
            return prices.equals(sorted);
        } catch (Exception e) {
            return false;
        }
    }
}
