package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    WebDriver driver;

    By loginButtonMenu = By.xpath("//button[normalize-space()='Login']");

    By emailField = By.xpath("//input[@type='email']");

    By passwordField = By.xpath("//input[@type='password']");

    By popupLoginButton = By.cssSelector("button.btn.btn-lg.btn-secondary");

    By nicknameField = By.xpath("//input[@placeholder='Nickname']");

    By continueButton = By.xpath("//button[contains(text(),'Continue')]");

    By closePopupButton = By.cssSelector("div.dialog-close");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLoginPopup() throws InterruptedException {

        Thread.sleep(5000);

        WebElement button = driver.findElement(loginButtonMenu);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);

        Thread.sleep(2000);
    }

    public void enterEmail(String email) {

        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) throws InterruptedException {

        driver.findElement(passwordField).sendKeys(password);

        Thread.sleep(2000);
    }

    public void clickPopupLogin() throws InterruptedException {

        Thread.sleep(4000);

        WebElement loginBtn = driver.findElement(popupLoginButton);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", loginBtn);

        Thread.sleep(1000);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", loginBtn);

        System.out.println("Popup login clicked");
    }

    public void enterNickname(String nickname) throws InterruptedException {

        Thread.sleep(3000);

        driver.findElement(nicknameField).sendKeys(nickname);
    }

    public void clickContinue() throws InterruptedException {

        Thread.sleep(2000);

        driver.findElement(continueButton).click();

        System.out.println("Continue clicked");
    }

    public void closePopup() throws InterruptedException {

        Thread.sleep(3000);

        driver.findElement(closePopupButton).click();
    }
}