package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage extends BasePage {

    private final By nameOnCard = By.name("name_on_card");
    private final By cardNumber = By.name("card_number");
    private final By cvc = By.name("cvc");
    private final By expiryMonth = By.name("expiry_month");
    private final By expiryYear = By.name("expiry_year");
    private final By payAndConfirmBtn = By.id("submit");
    private final By orderPlacedMessage = By.xpath(
            "//p[contains(text(),'Your order has been placed successfully') or contains(text(),'Congratulations! Your order has been confirmed!')]"
    );

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void enterPaymentDetails(
            String cardHolderName,
            String cardNum,
            String cvcValue,
            String month,
            String year
    ) {
        type(nameOnCard, cardHolderName);
        type(cardNumber, cardNum);
        type(cvc, cvcValue);
        type(expiryMonth, month);
        type(expiryYear, year);
    }

    public void clickPayAndConfirm() {
        safeClick(payAndConfirmBtn);
    }

    public boolean isOrderSuccessVisible() {
        return isVisible(orderPlacedMessage);
    }
}
