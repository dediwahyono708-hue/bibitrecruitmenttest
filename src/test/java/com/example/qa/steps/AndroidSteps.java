package com.example.qa.steps;

import com.example.qa.driver.AppiumDriverFactory;
import com.example.qa.pages.CartPage;
import com.example.qa.pages.LoginPage;
import com.example.qa.pages.ProductsPage;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import com.example.qa.pages.CheckoutPage;

public class AndroidSteps {
    private AppiumDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;

    @Given("the app is started and I am on the login screen")
    public void app_started_on_login() throws Exception {
        driver = AppiumDriverFactory.getDriver();
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
    }

    @When("I login with username {string} and password {string}")
    public void i_login(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.tapLogin();
    }

    @Then("I should see the products list")
    public void should_see_products() {
        Assert.assertTrue("Products list should be visible", productsPage.isOnProductsList());
    }

    @When("I add item {string} with color {string} quantity {int} to the cart")
    public void add_item_to_cart(String name, String color, int qty) {
        // follow provided flow: open first product, select color, increase qty, add to cart
        productsPage.openFirstProduct();
        productsPage.selectColor(color + " color");
        // increase quantity (we assume starting at 1, so increase qty-1 times)
        if (qty > 1) productsPage.increaseQuantity(qty - 1);
        int detailsQty = productsPage.readQuantityFromDetails();
        // simple assert here - if mismatch tests will fail
        if (detailsQty != -1) {
            Assert.assertEquals("Quantity on details page", qty, detailsQty);
        }
        productsPage.tapAddToCartFromDetails();
        // open cart and verify
        productsPage.openCartIcon();
        // view selected product in cart and confirm
        productsPage.openSelectedProductInCart();
        String selColor = cartPage.getSelectedColor();
        String norm = (selColor == null) ? "" : selColor.replaceAll("\\s+", " ").toLowerCase();
        boolean contains = norm.contains(color.toLowerCase());
        if (!contains) {
            // dump page source for debugging
            try {
                System.out.println("[DEBUG] Selected color mismatch. elementText='" + selColor + "'");
                // save page source and screenshot to target for offline inspection
                String ts = String.valueOf(System.currentTimeMillis());
                try {
                    java.nio.file.Path outDir = java.nio.file.Paths.get("target/test-artifacts");
                    java.nio.file.Files.createDirectories(outDir);
                    java.nio.file.Path ps = outDir.resolve("pagesource-" + ts + ".xml");
                    java.nio.file.Files.writeString(ps, driver.getPageSource());
                    System.out.println("[DEBUG] Saved page source to: " + ps.toAbsolutePath());
                } catch (Exception ioe) {
                    System.out.println("[DEBUG] Failed saving page source: " + ioe.getMessage());
                }
                try {
                    java.nio.file.Path outDir = java.nio.file.Paths.get("target/test-artifacts");
                    java.nio.file.Path img = outDir.resolve("screenshot-" + ts + ".png");
                    byte[] bytes = driver.getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                    java.nio.file.Files.write(img, bytes);
                    System.out.println("[DEBUG] Saved screenshot to: " + img.toAbsolutePath());
                } catch (Exception ioe) {
                    System.out.println("[DEBUG] Failed saving screenshot: " + ioe.getMessage());
                }
                System.out.println("[DEBUG] PageSource (console):\n" + driver.getPageSource());
            } catch (Exception ignored) {
            }
        }
        Assert.assertTrue("Selected color should contain '" + color + "' but was '" + selColor + "'", contains);
        int cartQty = cartPage.readQuantityInCart();
        System.out.println("[DEBUG] Quantity in cart: " + cartQty);
        if (cartQty != -1) Assert.assertEquals("Quantity in cart", qty, cartQty);
        
    }

    @When("I complete checkout with sample data")
    public void complete_checkout_with_sample_data() {
        CheckoutPage checkout = new CheckoutPage(driver);
        // sample data; you can make these parameters or read from properties
        checkout.fillCustomerInfo("Full Name","Address","Address 2","city","state","8888","United State");
        checkout.fillPayment("full name","99999999","03/30","123");
        checkout.placeOrder();
    }

    @Then("the order should be successful")
    public void order_should_be_successful() {
        CheckoutPage checkout = new CheckoutPage(driver);
        Assert.assertTrue("Order should be successful", checkout.verifyOrderSuccess());
    }

    @Then("the cart should contain {int} items of {string}")
    public void cart_should_contain(int expectedQty, String itemName) {
        int actual = cartPage.readQuantityInCart();
        System.out.println("[DEBUG] Cart quantity for '" + itemName + "': " + actual);
        Assert.assertEquals("Cart quantity mismatch for " + itemName, expectedQty, actual);
        // proceed to confirm products for checkout
        cartPage.proceedToCheckout();
    }

    @When("I sort products by name descending")
    public void sort_name_desc() {
        productsPage.sortByNameDescending();
    }

    @When("I sort products by price ascending")
    public void sort_price_asc() {
        productsPage.sortByPriceAscending();
    }

    @Then("the products list should be sorted by name descending and price ascending")
    public void verify_sorted() {
        boolean namesOk = productsPage.verifyNameDescending();
        boolean pricesOk = productsPage.verifyPriceAscending();
        Assert.assertTrue("Name descending check failed", namesOk);
        Assert.assertTrue("Price ascending check failed", pricesOk);
    }
}
