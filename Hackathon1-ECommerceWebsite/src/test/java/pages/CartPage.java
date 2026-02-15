package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By cartHeading = By.xpath("//li[contains(text(),'Shopping Cart')]");
    private final By proceedToCheckoutBtn = By.xpath("//a[contains(text(),'Proceed To Checkout')]");
    private final By registerLoginFromModal = By.xpath("//u[contains(text(),'Register / Login')]");

    private final By subscriptionHeader = By.xpath("//h2[text()='Subscription']");
    private final By subscriptionEmailInput = By.id("susbscribe_email");
    private final By subscriptionSubmitBtn = By.id("subscribe");
    private final By subscriptionSuccessMessage = By.xpath("//div[contains(text(),'You have been successfully subscribed!')]");

    private final By cartRows = By.xpath("//tr[starts-with(@id,'product-')]");
    private final By rowPrice = By.xpath(".//td[@class='cart_price']/p");
    private final By rowQuantity = By.xpath(".//td[contains(@class,'cart_quantity')]//button");
    private final By rowTotal = By.xpath(".//td[@class='cart_total']/p");

    private final By firstDeleteBtn = By.xpath("(//a[@class='cart_quantity_delete'])[1]");
    private final By emptyCartMessage = By.xpath("//b[contains(text(),'Cart is empty!')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageVisible() {
        return isVisible(cartHeading);
    }

    public void clickProceedToCheckout() {
        safeClick(proceedToCheckoutBtn);
    }

    public void clickRegisterLoginFromCheckoutModal() {
        safeClick(registerLoginFromModal);
    }

    public void scrollToFooter() {
        scrollToBottom();
    }

    public boolean isSubscriptionVisible() {
        return isVisible(subscriptionHeader);
    }

    public void subscribe(String email) {
        type(subscriptionEmailInput, email);
        safeClick(subscriptionSubmitBtn);
    }

    public boolean isSubscriptionSuccessVisible() {
        return isVisible(subscriptionSuccessMessage)
                || driver.getPageSource().contains("You have been successfully subscribed!");
    }

    public int getCartRowCount() {
        return driver.findElements(cartRows).size();
    }

    public boolean hasAtLeastProducts(int expectedCount) {
        return getCartRowCount() >= expectedCount;
    }

    public String getFirstProductQuantity() {
        List<WebElement> rows = driver.findElements(cartRows);
        if (rows.isEmpty()) {
            return "";
        }
        return rows.get(0).findElement(rowQuantity).getText().trim();
    }

    public boolean areLineTotalsConsistent() {
        List<WebElement> rows = driver.findElements(cartRows);
        if (rows.isEmpty()) {
            return false;
        }

        for (WebElement row : rows) {
            int unitPrice = parseAmount(row.findElement(rowPrice).getText());
            int quantity = parseAmount(row.findElement(rowQuantity).getText());
            int lineTotal = parseAmount(row.findElement(rowTotal).getText());
            if ((unitPrice * quantity) != lineTotal) {
                return false;
            }
        }
        return true;
    }

    public void removeFirstProduct() {
        safeClick(firstDeleteBtn);
    }

    public boolean isCartEmpty() {
        return !driver.findElements(emptyCartMessage).isEmpty() || getCartRowCount() == 0;
    }

    private int parseAmount(String text) {
        String numeric = text.replaceAll("[^0-9]", "");
        if (numeric.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(numeric);
    }
}
