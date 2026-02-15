package tests;

import base.BaseTest;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataFactory;

public class AuthenticationTests extends BaseTest {

    @Test(description = "TC01 - Register User")
    public void tc01RegisterUser() {
        User user = TestDataFactory.buildUniqueUser();

        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickSignupLogin();
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "'New User Signup!' is not visible.");

        signupPage.enterSignupDetails(user.getName(), user.getEmail());
        Assert.assertTrue(signupPage.isEnterAccountInformationVisible(), "'Enter Account Information' is not visible.");

        signupPage.fillAccountDetails(user);
        Assert.assertTrue(accountStatusPage.isAccountCreatedVisible(), "'ACCOUNT CREATED!' is not visible.");
        accountStatusPage.clickContinue();

        Assert.assertTrue(homePage.isUserSessionActive(user.getName()), "User session is not active after registration.");

        homePage.clickDeleteAccount();
        Assert.assertTrue(accountStatusPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' is not visible.");
        accountStatusPage.clickContinue();
    }

    @Test(description = "TC02 - Login User with correct email and password")
    public void tc02LoginWithValidCredentials() {
        User user = registerUser();

        homePage.clickLogout();
        if (!signupPage.isLoginPageVisible()) {
            homePage.clickSignupLogin();
        }
        Assert.assertTrue(signupPage.isLoginPageVisible(), "Login page is not visible after logout.");

        signupPage.login(user.getEmail(), user.getPassword());
        Assert.assertTrue(homePage.isUserSessionActive(user.getName()), "User did not log in with valid credentials.");

        deleteAccount();
    }

    @Test(description = "TC03 - Login User with incorrect email and password")
    public void tc03LoginWithInvalidCredentials() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickSignupLogin();
        Assert.assertTrue(signupPage.isLoginPageVisible(), "Login page is not visible.");

        signupPage.login("wrong.user@mailinator.com", "wrongPassword");

        String errorMessage = signupPage.getInvalidLoginErrorMessage();
        Assert.assertTrue(
                errorMessage.contains("Your email or password is incorrect!"),
                "Expected invalid login message is not displayed. Actual: " + errorMessage
        );
    }

    @Test(description = "TC04 - Logout User")
    public void tc04LogoutUser() {
        User user = registerUser();

        homePage.clickLogout();
        if (!signupPage.isLoginPageVisible()) {
            homePage.clickSignupLogin();
        }
        Assert.assertTrue(signupPage.isLoginPageVisible(), "User is not redirected to login page after logout.");

        // Cleanup for repeatable test runs.
        signupPage.login(user.getEmail(), user.getPassword());
        deleteAccount();
    }

    @Test(description = "TC05 - Register User with existing email")
    public void tc05RegisterWithExistingEmail() {
        User user = registerUser();

        homePage.clickLogout();
        if (!signupPage.isNewUserSignupVisible()) {
            homePage.clickSignupLogin();
        }
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "Signup block is not visible.");

        signupPage.enterSignupDetails("DuplicateUser", user.getEmail());

        String errorMessage = signupPage.getExistingEmailErrorMessage();
        Assert.assertTrue(
                errorMessage.contains("Email Address already exist!"),
                "Expected existing-email error is not displayed. Actual: " + errorMessage
        );

        signupPage.login(user.getEmail(), user.getPassword());
        deleteAccount();
    }

    private User registerUser() {
        User user = TestDataFactory.buildUniqueUser();

        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickSignupLogin();
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "'New User Signup!' is not visible.");

        signupPage.enterSignupDetails(user.getName(), user.getEmail());
        Assert.assertTrue(signupPage.isEnterAccountInformationVisible(), "'Enter Account Information' is not visible.");

        signupPage.fillAccountDetails(user);
        Assert.assertTrue(accountStatusPage.isAccountCreatedVisible(), "Account creation failed.");
        accountStatusPage.clickContinue();

        Assert.assertTrue(homePage.isUserSessionActive(user.getName()), "User session is not active after registration.");
        return user;
    }

    private void deleteAccount() {
        Assert.assertTrue(homePage.isLogoutVisible(), "Logout link is not visible.");
        homePage.clickDeleteAccount();
        Assert.assertTrue(accountStatusPage.isAccountDeletedVisible(), "Account deletion failed.");
        accountStatusPage.clickContinue();
    }
}
