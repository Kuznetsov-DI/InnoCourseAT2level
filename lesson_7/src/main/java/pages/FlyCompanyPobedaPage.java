package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Waiter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FlyCompanyPobedaPage {

    private final WebDriver driver;
    private final JavascriptExecutor js;

    @FindBy(css = "img[alt='«Авиакомпания «Победа», Группа «Аэрофлот»']")
    private WebElement logo;

    @FindBy(css = "a[href='/information']")
    private WebElement informationMenu;

    @FindBy(css = "div.dp-1isywux-root")
    private WebElement informationPopupWindow;

    @FindBy(css = "form div.dp-1dbdmhf-root")
    private WebElement topMenuForm;

    @FindBy(xpath = "//span[text()='Управление бронированием']/..")
    private WebElement bookingManagementButton;

    @FindBy(css = "form div.dp-1amht5s-root input[placeholder='Откуда']:not([readonly]")
    private WebElement fromFieldOfSearchTicketsForm;

    @FindBy(css = "form div.dp-1amht5s-root input[placeholder='Куда']:not([readonly]")
    private WebElement toFieldOfSearchTicketsForm;

    @FindBy(css = "form div.dp-1amht5s-root input[placeholder='Фамилия клиента']")
    private WebElement clientNameFieldOfBookingManagementForm;

    @FindBy(css = "form div.dp-1amht5s-root input[placeholder='Номер бронирования или билета']")
    private WebElement ticketIrManagementNumberFieldOfBookingManagementForm;

    @FindBy(css = "form div.dp-1amht5s-root div.dp-20s1up-root-suggestionName")
    private WebElement firstElementFromDownListInFieldSearchTicketsForm;

    @FindBy(css = "form div.dp-1dbdmhf-root button[type='submit']")
    private WebElement searchSubmitButton;

    @FindBy(css = "form div.dp-1amht5s-root input[placeholder='Туда']")
    private WebElement fromDateFieldOfSearchTicketsForm;

    public FlyCompanyPobedaPage(final WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public FlyCompanyPobedaPage checkTitleText(String text) {
        var title = driver.getTitle();
        Assertions.assertNotNull(title);
        Assertions.assertEquals(text, title, "Заголовок не содержит текст - " + text);

        return this;
    }

    public FlyCompanyPobedaPage checkLogoPresent() {
        var isPresent = logo.isDisplayed();
        Assertions.assertTrue(isPresent);

        return this;
    }

    public FlyCompanyPobedaPage moveCursorToInformationMenu() {
        Waiter.waitUntilElementToBeClickable(driver, informationMenu);
        new Actions(driver).moveToElement(informationMenu).pause(500).perform();

        return this;
    }

    public void checkTextPresentInInformationPopupWindow(String... text) {
        Waiter.waitUntilVisible(driver, informationPopupWindow);
        var textPopupWindow = informationPopupWindow.getText();

        for (String item : text) {
            Assertions.assertTrue(textPopupWindow.contains(item), "Текст - " + item + " не был найден в всплывающем окне");
        }
    }

    public FlyCompanyPobedaPage scrollToSearchTicketsFrom() {
        Waiter.waitUntilVisible(driver, topMenuForm);
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", topMenuForm);

        return this;
    }

    public FlyCompanyPobedaPage scrollToBookingManagementButtonAndClick() {
        Waiter.waitUntilVisible(driver, bookingManagementButton);
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", bookingManagementButton);
        bookingManagementButton.click();

        return this;
    }

    public FlyCompanyPobedaPage checkInputFieldsTextPresentInTopMenuForm(String... text) {
        var inputPlaceholdersFromSearchTicketsForm = topMenuForm.findElements(By.cssSelector("input"))
                .stream()
                .map(el -> el.getAttribute("placeholder"))
                .collect(Collectors.toSet());

        for (String item : text) {
            Assertions.assertTrue(inputPlaceholdersFromSearchTicketsForm.contains(item), "Текст - " + item + " не был найден в полях ввода информации по билетам");
        }

        return this;
    }

    public FlyCompanyPobedaPage chooseFromFieldValue(String text) {
        fromFieldOfSearchTicketsForm.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        fromFieldOfSearchTicketsForm.sendKeys(text);
        firstElementFromDownListInFieldSearchTicketsForm.click();

        return this;
    }

    public FlyCompanyPobedaPage chooseToFieldValue(String text) {
        toFieldOfSearchTicketsForm.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        toFieldOfSearchTicketsForm.sendKeys(text);
        firstElementFromDownListInFieldSearchTicketsForm.click();

        return this;
    }

    public FlyCompanyPobedaPage checkSubmitSearchPresent() {
        var isPresent = searchSubmitButton.isDisplayed();
        Assertions.assertTrue(isPresent);

        return this;
    }

    public FlyCompanyPobedaPage submitSearch() {
        searchSubmitButton.click();

        return this;
    }

    public void checkErrorDataFieldWhenEmpty() {
        boolean isError = Boolean.parseBoolean(fromDateFieldOfSearchTicketsForm.findElement(By.xpath("..")).getAttribute("data-failed"));

        Assertions.assertTrue(isError);
    }

    public FlyCompanyPobedaPage inputClientNameFieldValue(String text) {
        clientNameFieldOfBookingManagementForm.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        clientNameFieldOfBookingManagementForm.sendKeys(text);

        return this;
    }

    public FlyCompanyPobedaPage inputTicketOrManagementNumberFieldValue(String text) {
        ticketIrManagementNumberFieldOfBookingManagementForm.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        ticketIrManagementNumberFieldOfBookingManagementForm.sendKeys(text);

        return this;
    }

    public SearchTicketsPage switchToSearchTicketsPage() {
        Waiter.waitWindowsToBe(driver, 2);
        var allWindows = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(allWindows.get(1));

        return new SearchTicketsPage(driver);
    }
}
