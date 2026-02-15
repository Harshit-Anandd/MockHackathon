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

public class CartAndCheckoutTests extends BaseTest {

    @Test(description = "TC11 - Verify Subscription in Cart Page")
    public void tc11VerifySubscriptionInCartPage() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page is not visible.");

        cartPage.scrollToFooter();
        Assert.assertTrue(cartPage.isSubscriptionVisible(), "'SUBSCRIPTION' section is not visible on cart page.");

        cartPage.subscribe("ace.cart.sub." + System.currentTimeMillis() + "@mailinator.com");
        Assert.assertTrue(cartPage.isSubscriptionSuccessVisible(), "Subscription success message is not visible on cart page.");
    }

    @Test(description = "TC12 - Add Products in Cart")
    public void tc12AddProductsInCart() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isAllProductsVisible(), "All Products page is not visible.");

        productsPage.addProductToCartByIndex(1);
        productsPage.clickContinueShopping();
        productsPage.addProductToCartByIndex(2);
        homePage.clickViewCartFromModal();

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page is not visible.");
        Assert.assertTrue(cartPage.hasAtLeastProducts(2), "Expected at least 2 products in cart.");
        Assert.assertTrue(cartPage.areLineTotalsConsistent(), "Price x quantity does not match line totals in cart.");
    }

    @Test(description = "TC13 - Verify Product Quantity in Cart")
    public void tc13VerifyProductQuantityInCart() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.openFirstProductDetail();
        productsPage.setProductQuantity("4");
        productsPage.addCurrentDetailProductToCart();
        homePage.clickViewCartFromModal();

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageVisible(), "Cart page is not visible.");
        Assert.assertEquals(cartPage.getFirstProductQuantity(), "4", "Product quantity in cart is not 4.");
    }

    @Test(description = "TC14 - Place Order: Register while Checkout")
    public void tc14PlaceOrderRegisterWhileCheckout() {
        User user = TestDataFactory.buildUniqueUser();

        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        addFirstProductAndOpenCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.clickProceedToCheckout();
        cartPage.clickRegisterLoginFromCheckoutModal();

        signupPage.enterSignupDetails(user.getName(), user.getEmail());
        Assert.assertTrue(signupPage.isEnterAccountInformationVisible(), "'Enter Account Information' is not visible.");
        signupPage.fillAccountDetails(user);
        Assert.assertTrue(accountStatusPage.isAccountCreatedVisible(), "Account creation failed.");
        accountStatusPage.clickContinue();
        if (!homePage.isUserSessionActive(user.getName())) {
            homePage.clickSignupLogin();
            signupPage.login(user.getEmail(), user.getPassword());
        }
        Assert.assertTrue(homePage.isUserSessionActive(user.getName()), "User session is not active after registration.");

        homePage.clickCart();
        completeCheckoutAndPayment("TC14: Register while checkout order");
        deleteCurrentUserAccount();
    }

    @Test(description = "TC15 - Place Order: Register before Checkout")
    public void tc15PlaceOrderRegisterBeforeCheckout() {
        User user = TestDataFactory.buildUniqueUser();

        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickSignupLogin();
        signupPage.enterSignupDetails(user.getName(), user.getEmail());
        Assert.assertTrue(signupPage.isEnterAccountInformationVisible(), "'Enter Account Information' is not visible.");
        signupPage.fillAccountDetails(user);
        Assert.assertTrue(accountStatusPage.isAccountCreatedVisible(), "Account creation failed.");
        accountStatusPage.clickContinue();
        if (!homePage.isUserSessionActive(user.getName())) {
            homePage.clickSignupLogin();
            signupPage.login(user.getEmail(), user.getPassword());
        }
        Assert.assertTrue(homePage.isUserSessionActive(user.getName()), "User session is not active after registration.");

        addFirstProductAndOpenCart();
        completeCheckoutAndPayment("TC15: Register before checkout order");
        deleteCurrentUserAccount();
    }

    private void addFirstProductAndOpenCart() {
        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isAllProductsVisible(), "All Products page is not visible.");
        productsPage.addProductToCartByIndex(1);
        homePage.clickViewCartFromModal();
    }

    private void completeCheckoutAndPayment(String comment) {
        homePage.clickCart();
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
