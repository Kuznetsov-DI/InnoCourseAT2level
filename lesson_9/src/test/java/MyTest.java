import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.FlyCompanyPobedaPage;

@Epic("Авиакомпания \"Победа\"")
public class MyTest extends BaseTest {

    @Test
    @DisplayName("Всплывающее окно с информацией")
    @Feature("Всплывающее окно с информацией")
    @Description("Проверка отображения информации в всплывающем окне при наведении курсора на кнопку \"Информация\"")
    void firstTestPopupWindow() {
        new FlyCompanyPobedaPage()
                .checkTitleText("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками")
                .checkLogoPresent()
                .moveCursorToInformationMenu()
                .checkTextPresentInInformationPopupWindow("Подготовка к полёту", "Полезная информация", "О компании");
    }

    @Test
    @DisplayName("Ошибка поиска билетов без заполненной даты")
    @Feature("Ошибка поиска билетов без заполненной даты")
    @Description("Проверка изменения цвета поля ввода даты на красный, если оно пустое")
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
    @DisplayName("Ошибка поиска билета или номера бронирования, если его не существует")
    @Feature("Ошибка поиска билета или номера бронирования, если его не существует")
    @Description("Проверка получения сообщения об ошибке, если номера бронирования не существует")
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

    @Test
    @DisplayName("Падающий тест для отчета")
    @Feature("Падающий тест для отчета")
    @Description("Падающий тест для отчета")
    void failTestForReport() {
        new FlyCompanyPobedaPage()
                .checkTitleText("Черепашки мутанты ниндзя")
                .checkLogoPresent()
                .moveCursorToInformationMenu()
                .checkTextPresentInInformationPopupWindow("Подготовка к полёту", "Полезная информация", "О компании");
    }
}