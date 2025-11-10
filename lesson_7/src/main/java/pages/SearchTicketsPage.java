package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Waiter;

import java.util.stream.Collectors;

public class SearchTicketsPage {

    private final WebDriver driver;

    @FindBy(xpath = "//button[text()='Найти заказ']")
    private WebElement findOrderButton;

    @FindBy(css = "div.customCheckbox")
    private WebElement searchOrderAgreeChbCheckbox;

    @FindBy(css = "div[ng-if='vm.errorMessage']")
    private WebElement errorMessage;

    public SearchTicketsPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SearchTicketsPage activateSearchOrderAgreeChbCheckbox() {
        if (!searchOrderAgreeChbCheckbox.isSelected()) {
            searchOrderAgreeChbCheckbox.click();
        }

        return this;
    }

    public SearchTicketsPage clickFindOrderButton() {
        findOrderButton.click();

        return this;
    }

    public void checkErrorMessageText(String text) {
        Waiter.waitUntilVisible(driver, errorMessage);
        var actualText = errorMessage.getText();

        Assertions.assertEquals(text, actualText);
    }
}
