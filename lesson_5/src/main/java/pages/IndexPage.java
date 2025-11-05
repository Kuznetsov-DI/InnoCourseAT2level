package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndexPage {

    private final WebDriver driver;

    @FindBy(css = "button.header-right-menu__login-button")
    private WebElement enterButton;

    public IndexPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public IndexPage checkHeaderWebSite(String text) {
        var title = driver.getTitle();
        Assertions.assertNotNull(title);
        Assertions.assertEquals(text, title, "Заголовок не содержит текст - " + text);

        return this;
    }

    public AuthorizationModal clickEnterButton() {
        enterButton.click();

        return new AuthorizationModal(driver);
    }
}
