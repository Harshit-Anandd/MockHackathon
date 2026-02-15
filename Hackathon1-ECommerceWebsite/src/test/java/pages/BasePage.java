package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected boolean isVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
    }

    protected void type(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> findAll(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void selectByVisibleText(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Select(element).selectByVisibleText(value);
    }

    protected void safeClick(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (ElementClickInterceptedException | TimeoutException intercepted) {
            removeAdOverlays();
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            safeClick(element);
        }
    }

    protected void safeClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException | TimeoutException intercepted) {
            removeAdOverlays();
            scrollIntoView(element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected String currentUrl() {
        return driver.getCurrentUrl();
    }

    protected void stabilizeAgainstAds() {
        removeAdOverlays();
    }

    private void removeAdOverlays() {
        String script = ""
                + "const adSelectors = ["
                + "'#ad_position_box',"
                + "'.adsbygoogle',"
                + "'ins.adsbygoogle',"
                + "'iframe[id^=\"aswift_\"]',"
                + "'iframe[src*=\"googleads\"]',"
                + "'iframe[src*=\"doubleclick\"]',"
                + "'iframe[src*=\"googlesyndication\"]'"
                + "];"
                + "adSelectors.forEach(selector => {"
                + "  document.querySelectorAll(selector).forEach(el => el.remove());"
                + "});";

        ((JavascriptExecutor) driver).executeScript(script);
    }
}
