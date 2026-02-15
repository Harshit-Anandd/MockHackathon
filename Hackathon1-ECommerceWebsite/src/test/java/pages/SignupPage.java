package pages;

import models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {

    private final By signupNameField = By.name("name");
    private final By signupEmailField = By.xpath("//input[@data-qa='signup-email']");
    private final By signupBtn = By.xpath("//button[@data-qa='signup-button']");
    private final By newUserSignupHeader = By.xpath("//h2[text()='New User Signup!']");
    private final By enterAccountInfoHeader = By.xpath("//*[contains(text(),'Enter Account Information')]");

    private final By loginEmailField = By.xpath("//input[@data-qa='login-email']");
    private final By loginPasswordField = By.xpath("//input[@data-qa='login-password']");
    private final By loginBtn = By.xpath("//button[@data-qa='login-button']");
    private final By loginHeader = By.xpath("//h2[text()='Login to your account']");
    private final By invalidLoginError = By.xpath("//form[@action='/login']//p");
    private final By existingEmailError = By.xpath("//form[@action='/signup']//p");

    private final By titleMrRadio = By.id("id_gender1");
    private final By passwordField = By.id("password");
    private final By dayDropdown = By.id("days");
    private final By monthDropdown = By.id("months");
    private final By yearDropdown = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobile = By.id("mobile_number");
    private final By createAccountBtn = By.xpath("//button[@data-qa='create-account']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNewUserSignupVisible() {
        stabilizeAgainstAds();
        return isVisible(newUserSignupHeader);
    }

    public boolean isEnterAccountInformationVisible() {
        stabilizeAgainstAds();
        return isVisible(enterAccountInfoHeader);
    }

    public void enterSignupDetails(String name, String email) {
        type(signupNameField, name);
        type(signupEmailField, email);
        safeClick(signupBtn);
    }

    public void fillAccountDetails(User user) {
        safeClick(titleMrRadio);
        type(passwordField, user.getPassword());
        selectByVisibleText(dayDropdown, "10");
        selectByVisibleText(monthDropdown, "May");
        selectByVisibleText(yearDropdown, "1998");
        safeClick(newsletterCheckbox);
        safeClick(offersCheckbox);

        type(firstName, user.getFirstName());
        type(lastName, user.getLastName());
        type(company, user.getCompany());
        type(address, user.getAddress1());
        type(address2, user.getAddress2());
        selectByVisibleText(country, user.getCountry());
        type(state, user.getState());
        type(city, user.getCity());
        type(zipcode, user.getZipcode());
        type(mobile, user.getMobileNumber());

        safeClick(createAccountBtn);
    }

    public boolean isLoginPageVisible() {
        stabilizeAgainstAds();
        return isVisible(loginHeader);
    }

    public void login(String email, String password) {
        type(loginEmailField, email);
        type(loginPasswordField, password);
        safeClick(loginBtn);
    }

    public String getInvalidLoginErrorMessage() {
        return getText(invalidLoginError);
    }

    public String getExistingEmailErrorMessage() {
        return getText(existingEmailError);
    }
}
