import org.junit.jupiter.api.Test;
import pages.IndexPage;

public class MyTest extends BaseTest {

    @Test
    void test() {
        new IndexPage(driver)
                .checkHeaderWebSite("Горячее – самые интересные и обсуждаемые посты | Пикабу")
                .clickEnterButton()
                .checkAuthModalPresent()
                .checkLoginInputPresent()
                .checkPasswordInputPresent()
                .checkEnterButtonPresent()
                .inputLogin("Qwerty")
                .inputPassword("Qwerty")
                .clickEnterButton()
                .checkAuthErrorMessageText("Ошибка. Вы ввели неверные данные авторизации");
    }
}
