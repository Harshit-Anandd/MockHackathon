package pages;

import org.openqa.selenium.By;

public class ContactUsPage extends BasePage {

    private final By getInTouchHeader = By.xpath("//h2[contains(text(),'Get In Touch')]");
    private final By nameField = By.name("name");
    private final By emailField = By.name("email");
    private final By subjectField = By.name("subject");
    private final By messageField = By.name("message");
    private final By uploadFileInput = By.name("upload_file");
    private final By submitBtn = By.name("submit");
    private final By successMessage = By.cssSelector("div.status.alert.alert-success");
    private final By homeBtn = By.cssSelector("a.btn.btn-success");

    public ContactUsPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    public boolean isGetInTouchVisible() {
        return isVisible(getInTouchHeader);
    }

    public void fillForm(
            String name,
            String email,
            String subject,
            String message,
            String filePath
    ) {
        type(nameField, name);
        type(emailField, email);
        type(subjectField, subject);
        type(messageField, message);
        find(uploadFileInput).sendKeys(filePath);
    }

    public void submitAndAcceptAlert() {
        safeClick(submitBtn);
        try {
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {
            // Alert is intermittently skipped; keep flow resilient.
        }
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public void clickHome() {
        safeClick(homeBtn);
    }
}
