package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Waiter;

public class AuthorizationModal {

    private final WebDriver driver;

    @FindBy(css = "div.auth-modal")
    private WebElement authModal;

    @FindBy(css = "div.auth-modal input[placeholder='Логин']")
    private WebElement loginInput;

    @FindBy(css = "div.auth-modal input[placeholder='Пароль']")
    private WebElement passwordInput;

    @FindBy(xpath = "//div[contains(@class, 'auth-modal')]//button[.//span[text()='Войти']]")
    private WebElement enterButton;

    @FindBy(css = "div.auth__notice + span.auth__error")
    private WebElement authErrorMessage;

    public AuthorizationModal(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AuthorizationModal checkAuthModalPresent() {
        var result = authModal.isDisplayed();
        Assertions.assertTrue(result, "Модальное окно авторизации не отображается");

        return this;
    }

    public AuthorizationModal checkLoginInputPresent() {
        var result = loginInput.isDisplayed();
        Assertions.assertTrue(result, "Поле для ввода логина не отображается");

        return this;
    }

    public AuthorizationModal checkPasswordInputPresent() {
        var result = passwordInput.isDisplayed();
        Assertions.assertTrue(result, "Поле для ввода логина не отображается");

        return this;
    }

    public AuthorizationModal checkEnterButtonPresent() {
        var result = enterButton.isDisplayed();
        Assertions.assertTrue(result, "Кнопка \"Войти\" не отображается");

        return this;
    }

    public AuthorizationModal inputLogin(String login) {
        loginInput.sendKeys(login);

        return this;
    }

    public AuthorizationModal inputPassword(String password) {
        passwordInput.sendKeys(password);

        return this;
    }

    public AuthorizationModal clickEnterButton() {
        enterButton.click();

        return this;
    }

    public void checkAuthErrorMessageText(String text) {
        Waiter.waitUntilVisible(driver, authErrorMessage);
        var actualText = authErrorMessage.getText();
        Assertions.assertEquals(text, actualText);
    }
}
