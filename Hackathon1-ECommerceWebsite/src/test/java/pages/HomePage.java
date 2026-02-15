package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String BASE_URL = "https://automationexercise.com/";

    private final By signupLoginBtn = By.linkText("Signup / Login");
    private final By contactUsBtn = By.linkText("Contact us");
    private final By testCasesBtn = By.linkText("Test Cases");
    private final By productsBtn = By.linkText("Products");
    private final By cartBtn = By.linkText("Cart");
    private final By logoutBtn = By.linkText("Logout");
    private final By deleteAccountBtn = By.linkText("Delete Account");
    private final By loggedInAsLabel = By.xpath("//a[contains(.,'Logged in as')]");
    private final By continueShoppingBtn = By.xpath("//button[contains(text(),'Continue Shopping')]");
    private final By viewCartBtn = By.xpath("//u[contains(text(),'View Cart')]");
    private final By subscriptionHeader = By.xpath("//h2[text()='Subscription']");
    private final By subscriptionEmailInput = By.id("susbscribe_email");
    private final By subscriptionSubmitBtn = By.id("subscribe");
    private final By subscriptionSuccessMessage = By.xpath("//div[contains(text(),'You have been successfully subscribed!')]");
    private final By homePageMarker = By.cssSelector("div.logo.pull-left");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BASE_URL);
        stabilizeAgainstAds();
    }

    public boolean isHomePageVisible() {
        return isVisible(homePageMarker);
    }

    public void clickSignupLogin() {
        navigateByClickOrUrl(signupLoginBtn, "login");
    }

    public void clickContactUs() {
        navigateByClickOrUrl(contactUsBtn, "contact_us");
    }

    public void clickTestCases() {
        navigateByClickOrUrl(testCasesBtn, "test_cases");
    }

    public void clickProducts() {
        navigateByClickOrUrl(productsBtn, "products");
    }

    public void clickCart() {
        navigateByClickOrUrl(cartBtn, "view_cart");
    }

    public void clickContinueShopping() {
        safeClick(continueShoppingBtn);
    }

    public void clickViewCartFromModal() {
        navigateByClickOrUrl(viewCartBtn, "view_cart");
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

    public void clickLogout() {
        navigateByClickOrUrl(logoutBtn, "logout");
    }

    public boolean isLogoutVisible() {
        return isVisible(logoutBtn);
    }

    public void clickDeleteAccount() {
        navigateByClickOrUrl(deleteAccountBtn, "delete_account");
    }

    public boolean isLoggedInAsVisible(String userName) {
        if (!isVisible(loggedInAsLabel)) {
            return false;
        }
        String label = getText(loggedInAsLabel);
        return label.contains(userName);
    }

    public boolean isUserSessionActive(String userName) {
        return isLoggedInAsVisible(userName) || isLogoutVisible();
    }

    private void navigateByClickOrUrl(By locator, String relativePath) {
        try {
            safeClick(locator);
        } catch (TimeoutException clickFailed) {
            driver.get(BASE_URL + relativePath);
            stabilizeAgainstAds();
        }
    }
}
