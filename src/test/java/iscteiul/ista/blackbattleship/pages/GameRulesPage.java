package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GameRulesPage {

    WebDriver driver;
    WebDriverWait wait;

    By rulesTitle =
            By.xpath("//h2[contains(normalize-space(),'Rules of Battleship game online')]");

    By rulesDescription =
            By.xpath("//*[contains(normalize-space(),'Battleship is a turn-based 2-player game')]");

    By giftsTitle =
            By.xpath("//h3[contains(normalize-space(),'Gifts')]");

    By weaponsTitle =
            By.xpath("//h3[contains(normalize-space(),'Weapons')]");

    public GameRulesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void scrollToRulesSection() {
        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(rulesTitle)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", title);
    }

    public boolean rulesTitleIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(rulesTitle)
        ).isDisplayed();
    }

    public boolean rulesDescriptionIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(rulesDescription)
        ).isDisplayed();
    }

    public boolean giftsSectionIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(giftsTitle)
        ).isDisplayed();
    }

    public boolean weaponsSectionIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(weaponsTitle)
        ).isDisplayed();
    }
}