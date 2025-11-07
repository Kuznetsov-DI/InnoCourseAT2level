import org.junit.jupiter.api.Test;
import pages.GooglePage;

public class MyTest extends BaseTest {

    @Test
    void test() {
        new GooglePage(driver)
                .searchByText("Сайт компании Победа")
                .clickOnFirstLink()
                .checkTextOnBanner("Полетели в Калининград")
                .changeLanguageToEnglish()
                .checkPresentTextOnMainPage("Ticket search", "Online check-in", "Manage my booking");
    }
}
