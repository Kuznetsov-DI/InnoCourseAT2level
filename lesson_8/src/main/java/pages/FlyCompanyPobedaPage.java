package pages;

import com.codeborne.selenide.ScrollIntoViewOptions;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class FlyCompanyPobedaPage {

    private final SelenideElement logo = $("img[alt='«Авиакомпания «Победа», Группа «Аэрофлот»']");
    private final SelenideElement informationMenu = $("a[href='/information']");
    private final SelenideElement informationPopupWindow = $("div.dp-1isywux-root");
    private final SelenideElement topMenuForm = $("form div.dp-1dbdmhf-root");
    private final SelenideElement bookingManagementButton = $x("//span[text()='Управление бронированием']/..");
    private final SelenideElement fromFieldOfSearchTicketsForm = $("form div.dp-1amht5s-root input[placeholder='Откуда']:not([readonly])");
    private final SelenideElement toFieldOfSearchTicketsForm = $("form div.dp-1amht5s-root input[placeholder='Куда']:not([readonly])");
    private final SelenideElement clientNameFieldOfBookingManagementForm = $("form div.dp-1amht5s-root input[placeholder='Фамилия клиента']");
    private final SelenideElement ticketOrManagementNumberFieldOfBookingManagementForm = $("form div.dp-1amht5s-root input[placeholder='Номер бронирования или билета']");
    private final SelenideElement firstElementFromDownListInFieldSearchTicketsForm = $("form div.dp-1amht5s-root div.dp-20s1up-root-suggestionName");
    private final SelenideElement searchSubmitButton = $("form div.dp-1dbdmhf-root button[type='submit']");
    private final SelenideElement fromDateFieldOfSearchTicketsForm = $("form div.dp-1amht5s-root input[placeholder='Туда']");

    public FlyCompanyPobedaPage checkTitleText(String text) {
        var title = title();
        Assertions.assertNotNull(title);
        Assertions.assertEquals(text, title, "Заголовок не содержит текст - " + text);

        return this;
    }

    public FlyCompanyPobedaPage checkLogoPresent() {
        logo.shouldBe(visible);

        return this;
    }

    public FlyCompanyPobedaPage moveCursorToInformationMenu() {
        informationMenu.shouldBe(interactable);
        actions().moveToElement(informationMenu).pause(500).perform();

        return this;
    }

    public void checkTextPresentInInformationPopupWindow(String... text) {
        informationPopupWindow.shouldBe(visible);
        var textPopupWindow = informationPopupWindow.getText();

        for (String item : text) {
            Assertions.assertTrue(textPopupWindow.contains(item), "Текст - " + item + " не был найден в всплывающем окне");
        }
    }

    public FlyCompanyPobedaPage scrollToSearchTicketsForm() {
        topMenuForm.scrollIntoView(new ScrollIntoViewOptions(
                ScrollIntoViewOptions.Behavior.smooth,
                ScrollIntoViewOptions.Block.center,
                ScrollIntoViewOptions.Inline.center)
        ).shouldBe(visible);

        return this;
    }

    public FlyCompanyPobedaPage scrollToBookingManagementButtonAndClick() {
        bookingManagementButton.scrollIntoView(new ScrollIntoViewOptions(
                ScrollIntoViewOptions.Behavior.smooth,
                ScrollIntoViewOptions.Block.center,
                ScrollIntoViewOptions.Inline.center)
        ).shouldBe(clickable).click();

        return this;
    }

    public FlyCompanyPobedaPage checkInputFieldsTextPresentInTopMenuForm(String... text) {

        var inputPlaceholdersFromSearchTicketsForm = $$("form div.dp-1dbdmhf-root input")
                .stream()
                .map(el -> el.getAttribute("placeholder"))
                .collect(Collectors.toSet());

        for (String item : text) {
            Assertions.assertTrue(inputPlaceholdersFromSearchTicketsForm.contains(item),
                    "Текст - " + item + " не был найден в полях ввода информации по билетам");
        }
        return this;
    }

    public FlyCompanyPobedaPage chooseFromFieldValue(String text) {
        fromFieldOfSearchTicketsForm.shouldBe(visible).setValue(text);
        firstElementFromDownListInFieldSearchTicketsForm.click();

        return this;
    }

    public FlyCompanyPobedaPage chooseToFieldValue(String text) {
        toFieldOfSearchTicketsForm.shouldBe(visible).setValue(text);
        firstElementFromDownListInFieldSearchTicketsForm.click();

        return this;
    }

    public FlyCompanyPobedaPage checkSubmitSearchPresent() {
        searchSubmitButton.shouldBe(visible);

        return this;
    }

    public FlyCompanyPobedaPage submitSearch() {
        searchSubmitButton.shouldBe(visible).click();

        return this;
    }

    public void checkErrorDataFieldWhenEmpty() {
        boolean isError = Boolean.parseBoolean(fromDateFieldOfSearchTicketsForm.parent().getAttribute("data-failed"));

        Assertions.assertTrue(isError);
    }

    public FlyCompanyPobedaPage inputClientNameFieldValue(String text) {
        clientNameFieldOfBookingManagementForm.shouldBe(visible).setValue(text);

        return this;
    }

    public FlyCompanyPobedaPage inputTicketOrManagementNumberFieldValue(String text) {
        ticketOrManagementNumberFieldOfBookingManagementForm.shouldBe(visible).setValue(text);

        return this;
    }

    public SearchTicketsPage switchToSearchTicketsPage() {
        switchTo().window(1);

        return new SearchTicketsPage();
    }
}