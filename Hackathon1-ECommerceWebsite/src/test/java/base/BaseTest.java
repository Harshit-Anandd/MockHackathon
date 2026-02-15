package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.AccountStatusPage;
import pages.HomePage;
import pages.SignupPage;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected SignupPage signupPage;
    protected AccountStatusPage accountStatusPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        String browser = System.getProperty("browser", "edge").trim().toLowerCase();
        if ("chrome".equals(browser)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("--disable-popup-blocking");
            driver = new ChromeDriver(chromeOptions);
        } else {
            driver = new EdgeDriver(options);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));

        homePage = new HomePage(driver);
        signupPage = new SignupPage(driver);
        accountStatusPage = new AccountStatusPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
