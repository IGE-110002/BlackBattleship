package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RulesPage {

    WebDriver driver;

    By rulesTitle = By.xpath("//h2[contains(text(),'Rules of Battleship game online')]");

    public RulesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void scrollToRules() {

        WebElement rules = driver.findElement(rulesTitle);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", rules);
    }

    public boolean rulesAreDisplayed() {

        return driver.findElement(rulesTitle).isDisplayed();
    }
}