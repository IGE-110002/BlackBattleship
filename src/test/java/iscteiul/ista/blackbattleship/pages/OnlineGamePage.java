package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OnlineGamePage {

    WebDriver driver;

    By playOnlineButton =
            By.xpath("//span[contains(text(),'Play online')]");

    By nicknameField =
            By.xpath("//input[@placeholder='Nickname']");

    By continueButton =
            By.xpath("//button[contains(text(),'Continue')]");

    public OnlineGamePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickPlayOnline() throws InterruptedException {

        Thread.sleep(3000);

        WebElement playBtn =
                driver.findElement(playOnlineButton);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", playBtn);
    }

    public void enterNickname(String nickname)
            throws InterruptedException {

        Thread.sleep(2000);

        driver.findElement(nicknameField)
                .sendKeys(nickname);
    }

    public void clickContinue()
            throws InterruptedException {

        Thread.sleep(2000);

        driver.findElement(continueButton)
                .click();
    }
}
