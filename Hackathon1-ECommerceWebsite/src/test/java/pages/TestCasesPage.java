package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestCasesPage extends BasePage {

    private final By testCasesHeader = By.xpath("//b[contains(text(),'Test Cases')]");

    public TestCasesPage(WebDriver driver) {
        super(driver);
    }

    public boolean isTestCasesHeaderVisible() {
        return isVisible(testCasesHeader) || currentUrl().contains("/test_cases");
    }
}
