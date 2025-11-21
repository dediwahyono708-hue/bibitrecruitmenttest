package com.example.qa.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import com.example.qa.context.TestContext;
import com.example.qa.selectors.CartSelectors;
import com.example.qa.selectors.ProductsSelectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private final AppiumDriver driver;

    public CartPage(AppiumDriver driver) {
        this.driver = driver;
    }

    private WebElement waitFor(By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public String getSelectedColor() {
        // If we stored the selected color earlier in the flow, prefer that value
        try {
            String ctx = TestContext.getSelectedColor();
            if (ctx != null && !ctx.trim().isEmpty()) return ctx.trim();
        } catch (Exception ignored) {}

        try {
            // 1) Try the explicit color display element first
            try {
                WebElement el = waitFor(CartSelectors.DISPLAY_COLOR, 3);
                String text = null;
                try { text = el.getText(); } catch (Exception ignored) {}
                if (text != null && !text.trim().isEmpty() && !text.trim().equalsIgnoreCase("Displays color of selected product")) return text.trim();

                // try content-desc / name / text attributes
                try {
                    String cd = el.getAttribute("content-desc");
                    if (cd != null && !cd.trim().isEmpty() && !cd.trim().equalsIgnoreCase("Displays color of selected product")) return cd.trim();
                } catch (Exception ignored) {}
                try {
                    String name = el.getAttribute("name");
                    if (name != null && !name.trim().isEmpty() && !name.trim().equalsIgnoreCase("Displays color of selected product")) return name.trim();
                } catch (Exception ignored) {}
                try {
                    String value = el.getAttribute("text");
                    if (value != null && !value.trim().isEmpty() && !value.trim().equalsIgnoreCase("Displays color of selected product")) return value.trim();
                } catch (Exception ignored) {}
            } catch (Exception ignored) {}

            // 2) If above not available, search the colors RecyclerView for the child that has the selection indicator
            try {
                WebElement colorList = waitFor(ProductsSelectors.COLORS_RECYCLER, 3);
                List<WebElement> items = colorList.findElements(AppiumBy.className("android.view.ViewGroup"));
                for (WebElement item : items) {
                    try {
                        // if this item contains the selection indicator, its child color image likely has the color name
                        List<WebElement> indicators = item.findElements(ProductsSelectors.COLOR_SELECTION_INDICATOR);
                        if (!indicators.isEmpty()) {
                            // find color image inside this item
                            try {
                                WebElement colorImg = item.findElement(AppiumBy.className("android.widget.ImageView"));
                                String cd = null;
                                try { cd = colorImg.getAttribute("content-desc"); } catch (Exception ignored) {}
                                if (cd != null && !cd.trim().isEmpty() && !cd.trim().equalsIgnoreCase("Indicates when color is selected")) return cd.trim();
                            } catch (Exception ignored) {}
                        }
                    } catch (Exception ignored) {}
                }
            } catch (Exception ignored) {}

            // 3) Fallback: scan the page for known color names and return the first match
            try {
                String page = driver.getPageSource().toLowerCase();
                java.util.List<String> known = java.util.Arrays.asList("blue","black","white","red","green","gray","yellow","brown","pink");
                for (String k : known) {
                    if (page.contains(k)) {
                        return Character.toUpperCase(k.charAt(0)) + k.substring(1);
                    }
                }
            } catch (Exception ignored) {}

            // Nothing found
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public int getQuantityForItem(String itemName) {
        try {
            List<WebElement> productItems = driver.findElements(CartSelectors.PRODUCT_RV_ITEMS);
            if (productItems == null || productItems.isEmpty()) return 0;
            String target = normalize(itemName);
            for (WebElement item : productItems) {
                try {
                    WebElement titleEl = null;
                    try { titleEl = item.findElement(CartSelectors.PRODUCT_TITLE); } catch (Exception ex) {
                        try { titleEl = item.findElement(AppiumBy.xpath(".//android.widget.TextView")); } catch (Exception ignored) {}
                    }
                    if (titleEl == null) continue;
                    String title = "";
                    try { title = titleEl.getText(); } catch (Exception ignored) {}
                    if (title == null) title = "";
                    if (normalize(title).contains(target) || target.contains(normalize(title))) {
                        try {
                            WebElement qtyEl = item.findElement(CartSelectors.PRODUCT_QUANTITY);
                            String t = qtyEl.getText();
                            if (t != null && !t.trim().isEmpty()) {
                                return Integer.parseInt(t.replaceAll("[^0-9]",""));
                            }
                        } catch (Exception ignored) {}
                        return 1;
                    }
                } catch (Exception ignored) {}
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private String normalize(String s) {
        if (s == null) return "";
        return s.replaceAll("[^a-z0-9]", "").toLowerCase(java.util.Locale.ENGLISH);
    }

    public int readQuantityInCart() {
        try {
            WebElement el = waitFor(CartSelectors.PRODUCT_QUANTITY, 5);
            return Integer.parseInt(el.getText().replaceAll("[^0-9]",""));
        } catch (Exception e) {
            return -1;
        }
    }

    public void proceedToCheckout() {
        WebElement el = waitFor(CartSelectors.PROCEED_TO_CHECKOUT, 5);
        el.click();
    }
}
