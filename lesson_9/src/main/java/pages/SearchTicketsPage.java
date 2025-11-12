package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchTicketsPage {
    private final SelenideElement findOrderButton = $x("//button[text()='Найти заказ']");
    private final SelenideElement searchOrderAgreeChbCheckbox = $("div.customCheckbox");
    private final SelenideElement errorMessage = $("div[ng-if='vm.errorMessage']");

    @Step("Активация чекбокса пользовательского соглашения")
    public SearchTicketsPage activateSearchOrderAgreeChbCheckbox() {
        if (!searchOrderAgreeChbCheckbox.shouldBe(clickable).isSelected()) {
            searchOrderAgreeChbCheckbox.click();
        }
        return this;
    }

    @Step("Нажатие на кнопку \"Найти заказ\"")
    public SearchTicketsPage clickFindOrderButton() {
        findOrderButton.shouldBe(visible).click();
        return this;
    }

    @Step("Проверка появления сообщения с ошибкой - {0}")
    public void checkErrorMessageText(String text) {
        errorMessage.shouldBe(visible).shouldHave(text(text));
    }
}
