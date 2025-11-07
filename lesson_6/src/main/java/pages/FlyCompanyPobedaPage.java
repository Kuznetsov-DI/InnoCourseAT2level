package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Waiter;

public class FlyCompanyPobedaPage {

    private final WebDriver driver;

    @FindBy(css = "div.dp-1exec1i-root-fullHeight")
    private WebElement topBanner;

    @FindBy(css = "button.dp-etauof-root-root")
    private WebElement changeLanguageButton;

    @FindBy(xpath = "//div[text()='English']")
    private WebElement englishLanguageItem;

    @FindBy(id = "__next")
    private WebElement fullPage;

    public FlyCompanyPobedaPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public FlyCompanyPobedaPage checkTextOnBanner(String text) {
        Waiter.waitUntilTextToBePresent(driver, topBanner, text);
        var bannerText = topBanner.getText();

        Assertions.assertTrue(bannerText.contains(text));

        return this;
    }

    public FlyCompanyPobedaPage changeLanguageToEnglish() {
        changeLanguageButton.click();
        englishLanguageItem.click();

        return this;
    }

    public void checkPresentTextOnMainPage(String... text) {
        var fullPadeText = fullPage.getText();

        for (String item : text) {
            Assertions.assertTrue(fullPadeText.contains(item), "Текст - " + item + " не был найден на странице");
        }
    }
}
