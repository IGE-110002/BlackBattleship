package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;

    WebDriverWait wait;

    By loginButtonMenu =
            By.xpath("//button[normalize-space()='Login']");

    By emailField =
            By.id("mat-input-serverApp0");

    By passwordField =
            By.id("mat-input-serverApp1");

    By popupLoginButton =
            By.cssSelector(
                    ".w-100:nth-child(1) > .btn-secondary > .front");

    By cookieConsentButton =
            By.xpath(
                    "//button[@aria-label='Consent' or normalize-space(.)='Consent']");

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openLoginPopup()
            throws InterruptedException {

        dismissCookiePopupIfPresent();

        Thread.sleep(3000);

        WebElement button =
                driver.findElement(loginButtonMenu);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);

        Thread.sleep(2000);
    }

    public void enterEmail(String email) {

        driver.findElement(emailField)
                .sendKeys(email);
    }

    public void enterPassword(String password)
            throws InterruptedException {

        driver.findElement(passwordField)
                .sendKeys(password);

        Thread.sleep(2000);
    }

    public void clickPopupLogin()
            throws InterruptedException {

        Thread.sleep(3000);

        WebElement loginBtn =
                driver.findElement(popupLoginButton);

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView(true);",
                        loginBtn);

        Thread.sleep(1000);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", loginBtn);
    }

    private void dismissCookiePopupIfPresent() {

        try {

            WebElement button =
                    new WebDriverWait(driver, Duration.ofSeconds(3))
                            .until(ExpectedConditions
                                    .presenceOfElementLocated(
                                            cookieConsentButton));

            if (button.isDisplayed()) {

                ((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                button);
            }

        } catch (TimeoutException | NoSuchElementException ignored) {
        }
    }
}