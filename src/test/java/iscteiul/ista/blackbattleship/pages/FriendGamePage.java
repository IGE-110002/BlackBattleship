package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FriendGamePage {

    WebDriver driver;
    WebDriverWait wait;

    By playWithFriendButton =
            By.xpath("//button[contains(normalize-space(),'Play with a friend')] | //a[contains(normalize-space(),'Play with a friend')]");

    By nicknameField =
            By.xpath("//input[@placeholder='Nickname']");

    By continueButton =
            By.xpath("//button[contains(normalize-space(),'Continue')]");

    public FriendGamePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickPlayWithFriend() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(playWithFriendButton)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nicknameField)
        );
    }

    public void enterNickname(String nickname) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(nicknameField)
        );

        field.clear();
        field.sendKeys(nickname);
    }

    public void clickContinue() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);
    }

    public boolean nicknameFieldIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(nicknameField)
        ).isDisplayed();
    }
}