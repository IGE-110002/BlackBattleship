package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SettingsPage {

    WebDriver driver;

    By settingsButton =
            By.cssSelector("button[mattooltip='Settings']");

    By darkModeButton =
            By.xpath("//*[contains(text(),'Dark mode')]");

    By closeSettingsButton =
            By.cssSelector(".dialog-close .mat-mdc-button-touch-target");

    public SettingsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openSettings()
            throws InterruptedException {

        Thread.sleep(3000);

        WebElement settings =
                driver.findElement(settingsButton);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", settings);
    }

    public void clickDarkMode()
            throws InterruptedException {

        Thread.sleep(2000);

        driver.findElement(darkModeButton).click();
    }

    public void clickSound()
            throws InterruptedException {

        Thread.sleep(2000);

        WebElement sound =
                driver.findElement(
                        By.xpath("//*[contains(text(),'Sound')]"));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", sound);

        System.out.println("Sound clicked");

        Thread.sleep(2000);
    }

    public void closeSettings()
            throws InterruptedException {

        Thread.sleep(2000);

        WebElement closeButton =
                driver.findElement(closeSettingsButton);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", closeButton);
    }
}