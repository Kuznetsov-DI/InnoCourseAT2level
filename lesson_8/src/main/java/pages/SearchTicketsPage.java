package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SearchTicketsPage {
    private final SelenideElement findOrderButton = $x("//button[text()='Найти заказ']");
    private final SelenideElement searchOrderAgreeChbCheckbox = $("div.customCheckbox");
    private final SelenideElement errorMessage = $("div[ng-if='vm.errorMessage']");

    public SearchTicketsPage activateSearchOrderAgreeChbCheckbox() {
        if (!searchOrderAgreeChbCheckbox.shouldBe(visible).isSelected()) {
            searchOrderAgreeChbCheckbox.click();
        }
        return this;
    }

    public SearchTicketsPage clickFindOrderButton() {
        findOrderButton.shouldBe(visible).click();
        return this;
    }

    public void checkErrorMessageText(String text) {
        errorMessage.shouldBe(visible).shouldHave(text(text));
    }
}
