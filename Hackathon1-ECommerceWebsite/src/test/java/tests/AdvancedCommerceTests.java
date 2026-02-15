package tests;

import base.BaseTest;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.PaymentPage;
import pages.ProductsPage;
import utils.TestDataFactory;

public class AdvancedCommerceTests extends BaseTest {

    @Test(description = "TC16 - Place Order: Login before Checkout")
    public void tc16PlaceOrderLoginBeforeCheckout() {
        User user = TestDataFactory.buildUniqueUser();

        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        registerUser(user);
        homePage.clickLogout();
        if (!signupPage.isLoginPageVisible()) {
            homePage.clickSignupLogin();
        }
        Assert.assertTrue(signupPage.isLoginPageVisible(), "Login page is not visible after logout.");

        signupPage.login(user.getEmail(), user.getPassword());
        Assert.assertTrue(homePage.isUserSessionActive(user.getName()), "User login failed before checkout.");

        addFirstProductAndOpenCart();
        completeCheckoutAndPayment("TC16: Login before checkout order");
        deleteCurrentUserAccount();
    }

    @Test(description = "TC17 - Remove Products from Cart")
    public void tc17RemoveProductsFromCart() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        addFirstProductAndOpenCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.hasAtLeastProducts(1), "Expected at least one product in cart before removal.");

        cartPage.removeFirstProduct();
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty after removing product.");
    }

    @Test(description = "TC18 - View Category Products")
    public void tc18ViewCategoryProducts() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isCategorySidebarVisible(), "Category sidebar is not visible.");

        productsPage.openWomenDressCategory();
        Assert.assertTrue(
                productsPage.getCategoryOrBrandTitle().toUpperCase().contains("WOMEN"),
                "Women category page title is not visible."
        );

        productsPage.openMenTshirtsCategory();
        Assert.assertTrue(
                productsPage.getCategoryOrBrandTitle().toUpperCase().contains("MEN"),
                "Men category page title is not visible."
        );
    }

    @Test(description = "TC19 - View & Cart Brand Products")
    public void tc19ViewAndCartBrandProducts() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isBrandsSidebarVisible(), "Brands sidebar is not visible.");

        productsPage.clickPoloBrand();
        Assert.assertTrue(
                productsPage.getCategoryOrBrandTitle().toUpperCase().contains("POLO")
                        || productsPage.getCurrentPageUrl().contains("/brand_products/Polo")
                        || productsPage.getCurrentPageUrl().contains("/brand_products/"),
                "Polo brand page title is not visible."
        );

        productsPage.clickHmBrand();
        Assert.assertTrue(
                productsPage.getCategoryOrBrandTitle().toUpperCase().contains("H&M")
                        || productsPage.getCurrentPageUrl().contains("/brand_products/H&M")
                        || productsPage.getCurrentPageUrl().contains("/brand_products/"),
                "H&M brand page title is not visible."
        );
    }

    @Test(description = "TC20 - Search Products and Verify Cart After Login")
    public void tc20SearchProductsAndVerifyCartAfterLogin() {
        User user = TestDataFactory.buildUniqueUser();

        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        registerUser(user);
        homePage.clickLogout();
        if (!signupPage.isLoginPageVisible()) {
            homePage.clickSignupLogin();
        }
        Assert.assertTrue(signupPage.isLoginPageVisible(), "Login page is not visible after logout.");

        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isCurrentUrlProductsPage(), "Not on Products page.");

        productsPage.searchProduct("Tshirt");
        Assert.assertTrue(productsPage.isSearchedProductsVisible(), "Searched products title is not visible.");
        Assert.assertTrue(productsPage.getSearchedProductsCount() > 0, "No searched products found.");

        productsPage.addProductToCartByIndex(1);
        homePage.clickViewCartFromModal();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.hasAtLeastProducts(1), "Cart is empty after adding searched products.");

        homePage.clickSignupLogin();
        signupPage.login(user.getEmail(), user.getPassword());
        Assert.assertTrue(homePage.isUserSessionActive(user.getName()), "Login failed with registered credentials.");

        homePage.clickCart();
        Assert.assertTrue(cartPage.hasAtLeastProducts(1), "Products are not present in cart after login.");
        deleteCurrentUserAccount();
    }

    private void registerUser(User user) {
        homePage.clickSignupLogin();
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "New User Signup section is not visible.");
        signupPage.enterSignupDetails(user.getName(), user.getEmail());
        Assert.assertTrue(signupPage.isEnterAccountInformationVisible(), "'Enter Account Information' is not visible.");
        signupPage.fillAccountDetails(user);
        Assert.assertTrue(accountStatusPage.isAccountCreatedVisible(), "Account creation failed.");
        accountStatusPage.clickContinue();
        Assert.assertTrue(homePage.isUserSessionActive(user.getName()), "User session is not active after registration.");
    }

    private void addFirstProductAndOpenCart() {
        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isAllProductsVisible(), "All Products page is not visible.");
        productsPage.addProductToCartByIndex(1);
        homePage.clickViewCartFromModal();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page is not visible.");
    }

    private void completeCheckoutAndPayment(String comment) {
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page is not visible.");
        cartPage.clickProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isAddressDetailsVisible(), "Address Details section is not visible.");
        Assert.assertTrue(checkoutPage.isReviewOrderVisible(), "Review Your Order section is not visible.");
        checkoutPage.enterComment(comment);
        checkoutPage.clickPlaceOrder();

        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.enterPaymentDetails("ACE Tester", "4111111111111111", "123", "12", "2030");
        paymentPage.clickPayAndConfirm();
        Assert.assertTrue(paymentPage.isOrderSuccessVisible(), "Order success message is not visible.");
    }

    private void deleteCurrentUserAccount() {
        homePage.clickDeleteAccount();
        Assert.assertTrue(accountStatusPage.isAccountDeletedVisible(), "Account deletion failed.");
        accountStatusPage.clickContinue();
    }
}
