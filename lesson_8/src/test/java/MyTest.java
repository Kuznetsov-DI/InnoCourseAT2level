import org.junit.jupiter.api.Test;
import pages.FlyCompanyPobedaPage;

public class MyTest extends BaseTest {

    @Test
    void firstTestPopupWindow() {
        new FlyCompanyPobedaPage()
                .checkTitleText("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками")
                .checkLogoPresent()
                .moveCursorToInformationMenu()
                .checkTextPresentInInformationPopupWindow("Подготовка к полёту", "Полезная информация", "О компании");
    }

    @Test
    void secondTestSearchInitiation() {
        new FlyCompanyPobedaPage()
                .checkTitleText("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками")
                .checkLogoPresent()
                .scrollToSearchTicketsForm()
                .checkInputFieldsTextPresentInTopMenuForm("Откуда", "Куда", "Туда", "Обратно")
                .chooseFromFieldValue("Москва")
                .chooseToFieldValue("Санкт-Петербург")
                .submitSearch()
                .checkErrorDataFieldWhenEmpty();
    }


    @Test
    void thirdTestSearchResult() {
        new FlyCompanyPobedaPage()
                .checkTitleText("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками")
                .checkLogoPresent()
                .scrollToBookingManagementButtonAndClick()
                .checkInputFieldsTextPresentInTopMenuForm("Фамилия клиента", "Номер бронирования или билета")
                .checkSubmitSearchPresent()
                .inputClientNameFieldValue("Qwerty")
                .inputTicketOrManagementNumberFieldValue("XXXXXX")
                .submitSearch()
                .switchToSearchTicketsPage()
                .activateSearchOrderAgreeChbCheckbox()
                .clickFindOrderButton()
                .checkErrorMessageText("Заказ с указанными параметрами не найден");
    }
}