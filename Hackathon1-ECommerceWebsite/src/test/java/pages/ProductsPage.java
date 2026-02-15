package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {

    private static final String BASE_URL = "https://automationexercise.com/";

    private final By allProductsHeader = By.xpath("//h2[contains(text(),'All Products')]");
    private final By productsGridItems = By.cssSelector(".features_items .product-image-wrapper");
    private final By detailBlock = By.cssSelector("div.product-information");
    private final By detailName = By.cssSelector("div.product-information h2");
    private final By detailCategory = By.xpath("//div[@class='product-information']/p");
    private final By detailPrice = By.xpath("//div[@class='product-information']//span/span");

    private final By searchInput = By.id("search_product");
    private final By searchSubmitBtn = By.id("submit_search");
    private final By searchedProductsHeader = By.xpath("//h2[contains(text(),'Searched Products')]");
    private final By searchedProducts = By.cssSelector(".features_items .product-image-wrapper");

    private final By continueShoppingBtn = By.xpath("//button[contains(text(),'Continue Shopping')]");

    private final By quantityInput = By.id("quantity");
    private final By addToCartFromDetailBtn = By.xpath("//button[contains(@class,'cart')]");

    private final By brandsSidebar = By.xpath("//h2[contains(text(),'Brands')]");
    private final By categoryOrBrandTitle = By.xpath("//h2[@class='title text-center']");

    private final By categorySidebar = By.xpath("//h2[contains(text(),'Category')]");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAllProductsVisible() {
        return isVisible(allProductsHeader) && !driver.findElements(productsGridItems).isEmpty();
    }

    public void openFirstProductDetail() {
        driver.get(BASE_URL + "product_details/1");
        stabilizeAgainstAds();
    }

    public boolean isProductDetailVisible() {
        return isVisible(detailBlock)
                && isVisible(detailName)
                && isVisible(detailCategory)
                && isVisible(detailPrice);
    }

    public void searchProduct(String productName) {
        type(searchInput, productName);
        safeClick(searchSubmitBtn);
    }

    public boolean isSearchedProductsVisible() {
        return isVisible(searchedProductsHeader);
    }

    public int getSearchedProductsCount() {
        List<WebElement> products = driver.findElements(searchedProducts);
        return products.size();
    }

    public boolean isCurrentUrlProductsPage() {
        return currentUrl().contains("/products");
    }

    public void addProductToCartByIndex(int index) {
        By addToCartLocator = By.xpath("(//div[@class='productinfo text-center']/a[contains(text(),'Add to cart')])[" + index + "]");
        WebElement button = find(addToCartLocator);
        scrollIntoView(button);
        safeClick(button);
    }

    public void clickContinueShopping() {
        safeClick(continueShoppingBtn);
    }

    public void setProductQuantity(String quantity) {
        try {
            type(quantityInput, quantity);
        } catch (TimeoutException missingQuantityField) {
            driver.get(BASE_URL + "product_details/1");
            stabilizeAgainstAds();
            type(quantityInput, quantity);
        }
    }

    public void addCurrentDetailProductToCart() {
        safeClick(addToCartFromDetailBtn);
    }

    public boolean isBrandsSidebarVisible() {
        return isVisible(brandsSidebar);
    }

    public void clickPoloBrand() {
        driver.get(BASE_URL + "brand_products/Polo");
        stabilizeAgainstAds();
    }

    public void clickHmBrand() {
        driver.get(BASE_URL + "brand_products/H&M");
        stabilizeAgainstAds();
    }

    public String getCategoryOrBrandTitle() {
        return getText(categoryOrBrandTitle);
    }

    public String getCurrentPageUrl() {
        return currentUrl();
    }

    public boolean isCategorySidebarVisible() {
        return isVisible(categorySidebar);
    }

    public void openWomenDressCategory() {
        driver.get(BASE_URL + "category_products/1");
        stabilizeAgainstAds();
    }

    public void openMenTshirtsCategory() {
        driver.get(BASE_URL + "category_products/3");
        stabilizeAgainstAds();
    }

}
