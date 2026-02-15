package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.ProductsPage;
import pages.TestCasesPage;

import java.nio.file.Paths;

public class EngagementAndCatalogTests extends BaseTest {

    @Test(description = "TC06 - Contact Us Form")
    public void tc06ContactUsForm() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickContactUs();
        ContactUsPage contactUsPage = new ContactUsPage(driver);
        Assert.assertTrue(contactUsPage.isGetInTouchVisible(), "'GET IN TOUCH' is not visible.");

        String filePath = Paths.get("src", "test", "resources", "test_upload.txt").toAbsolutePath().toString();
        contactUsPage.fillForm(
                "ACE Tester",
                "ace.contact." + System.currentTimeMillis() + "@mailinator.com",
                "Contact form subject",
                "This is an automated contact form submission.",
                filePath
        );
        contactUsPage.submitAndAcceptAlert();

        Assert.assertTrue(
                contactUsPage.getSuccessMessage().contains("Success"),
                "Success message is not displayed after contact form submission."
        );

        contactUsPage.clickHome();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible after returning from Contact Us page.");
    }

    @Test(description = "TC07 - Verify Test Cases Page")
    public void tc07VerifyTestCasesPage() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickTestCases();
        TestCasesPage testCasesPage = new TestCasesPage(driver);
        Assert.assertTrue(testCasesPage.isTestCasesHeaderVisible(), "Test Cases page heading is not visible.");
    }

    @Test(description = "TC08 - Verify All Products and Product Detail Page")
    public void tc08VerifyAllProductsAndProductDetail() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isAllProductsVisible(), "All Products page is not visible.");

        productsPage.openFirstProductDetail();
        Assert.assertTrue(productsPage.isProductDetailVisible(), "Product detail section is incomplete or not visible.");
    }

    @Test(description = "TC09 - Search Product")
    public void tc09SearchProduct() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isAllProductsVisible(), "All Products page is not visible.");

        productsPage.searchProduct("Tshirt");
        Assert.assertTrue(productsPage.isSearchedProductsVisible(), "'SEARCHED PRODUCTS' title is not visible.");
        Assert.assertTrue(productsPage.getSearchedProductsCount() > 0, "No products were returned for the searched keyword.");
    }

    @Test(description = "TC10 - Verify Subscription in Home Page")
    public void tc10VerifySubscriptionInHomePage() {
        homePage.open();
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible.");

        homePage.scrollToFooter();
        Assert.assertTrue(homePage.isSubscriptionVisible(), "'SUBSCRIPTION' section is not visible on home page footer.");

        homePage.subscribe("ace.home.sub." + System.currentTimeMillis() + "@mailinator.com");
        Assert.assertTrue(homePage.isSubscriptionSuccessVisible(), "Subscription success message is not visible on home page.");
    }
}
