package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class Waiter {

    public static void waitUntilElementToBeClickable(final WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofMinutes(10)).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitUntilVisible(final WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitWindowsToBe(final WebDriver driver, int count) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.numberOfWindowsToBe(count));
    }
}
