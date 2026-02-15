package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountStatusPage extends BasePage {

    private final By accountCreatedLabel = By.xpath("//h2[@data-qa='account-created']");
    private final By accountDeletedLabel = By.xpath("//h2[@data-qa='account-deleted']");
    private final By continueBtn = By.xpath("//a[@data-qa='continue-button']");

    public AccountStatusPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountCreatedVisible() {
        if (!isVisible(accountCreatedLabel)) {
            return false;
        }
        return "ACCOUNT CREATED!".equalsIgnoreCase(getText(accountCreatedLabel));
    }

    public boolean isAccountDeletedVisible() {
        if (!isVisible(accountDeletedLabel)) {
            return false;
        }
        return "ACCOUNT DELETED!".equalsIgnoreCase(getText(accountDeletedLabel));
    }

    public void clickContinue() {
        safeClick(continueBtn);
    }
}
