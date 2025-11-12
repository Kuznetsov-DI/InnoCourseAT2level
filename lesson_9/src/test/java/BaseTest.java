import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class BaseTest {

    private String url;

    @BeforeEach
    public void setUp() {
        Properties properties = new Properties();
        try (InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Файл config.properties не найден в ресурсах");
                return;
            }
            properties.load(input);
            url = properties.getProperty("url");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла properties: " + e.getMessage());
        }

        Configuration.browser = "chrome";
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--incognito")
                .addArguments("--disable-blink-features=AutomationControlled")
                .addArguments("--start-maximized")
                .addArguments("--disable-popup-blocking")
                .addArguments("--allow-popups");
        Configuration.pageLoadTimeout = 300000;
        Configuration.timeout = 15000;
        Configuration.baseUrl = url;
        Configuration.headless = false;

        Selenide.open("/");
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWindow();
    }
}
