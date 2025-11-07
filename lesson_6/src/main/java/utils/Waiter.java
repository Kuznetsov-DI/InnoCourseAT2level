package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class Waiter {

    public static void waitUntilTextToBePresent(final WebDriver driver, WebElement element, String text) {
        new WebDriverWait(driver, Duration.ofMinutes(5)).until(ExpectedConditions.textToBePresentInElement(element, text));
    }
}
