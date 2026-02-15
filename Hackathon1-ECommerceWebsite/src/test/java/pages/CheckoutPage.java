package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private final By addressDetailsHeader = By.xpath("//h2[contains(text(),'Address Details')]");
    private final By reviewOrderHeader = By.xpath("//h2[contains(text(),'Review Your Order')]");
    private final By commentTextArea = By.name("message");
    private final By placeOrderBtn = By.xpath("//a[contains(text(),'Place Order')]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddressDetailsVisible() {
        return isVisible(addressDetailsHeader);
    }

    public boolean isReviewOrderVisible() {
        return isVisible(reviewOrderHeader);
    }

    public void enterComment(String comment) {
        type(commentTextArea, comment);
    }

    public void clickPlaceOrder() {
        safeClick(placeOrderBtn);
    }
}
