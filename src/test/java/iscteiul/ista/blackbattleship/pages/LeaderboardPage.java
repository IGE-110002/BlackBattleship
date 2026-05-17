package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LeaderboardPage {

    WebDriver driver;
    WebDriverWait wait;

    By dailyLeaderboardText =
            By.xpath("//*[contains(normalize-space(),'Daily leaderboard')]");

    By seeAllButton =
            By.xpath("(//button[contains(normalize-space(),'See all')] | //a[contains(normalize-space(),'See all')])[1]");

    By rankingWindow =
            By.xpath("//*[contains(normalize-space(),'General ranking') or contains(normalize-space(),'RANKA') or contains(normalize-space(),'Daily leaderboard')]");

    By generalRankingText =
            By.xpath("//*[contains(normalize-space(),'General ranking')]");

    public LeaderboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void scrollToLeaderboardSection() {
        WebElement leaderboard = wait.until(
                ExpectedConditions.visibilityOfElementLocated(dailyLeaderboardText)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", leaderboard);
    }

    public boolean dailyLeaderboardIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(dailyLeaderboardText)
        ).isDisplayed();
    }

    public void clickSeeAllRankings() {
        scrollToLeaderboardSection();

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(seeAllButton)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);
    }

    public boolean rankingWindowIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(rankingWindow)
        ).isDisplayed();
    }

    public boolean generalRankingIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(generalRankingText)
        ).isDisplayed();
    }
}