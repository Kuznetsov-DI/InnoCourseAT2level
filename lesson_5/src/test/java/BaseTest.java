import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

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

        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }
}
