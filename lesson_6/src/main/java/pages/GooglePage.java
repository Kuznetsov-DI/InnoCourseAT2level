package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GooglePage {

    private final WebDriver driver;

    @FindBy(css = "textarea[aria-label='Найти']")
    private WebElement searchInput;

    @FindBy(css = "div[id='rso'] h3")
    private WebElement firstLink;

    public GooglePage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public GooglePage searchByText(String text) {
        searchInput.sendKeys(text, Keys.ENTER);

        return this;
    }

    public FlyCompanyPobedaPage clickOnFirstLink() {
        firstLink.click();

        return new FlyCompanyPobedaPage(driver);
    }
}
