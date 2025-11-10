import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public abstract class BaseTest {

    protected WebDriver driver;

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

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(5));
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }
}
