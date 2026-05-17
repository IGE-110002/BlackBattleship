package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateTournamentPage {

    WebDriver driver;
    WebDriverWait wait;

    By createTournamentLink =
            By.xpath("//a[normalize-space()='Create tournament']");

    By tournamentCreationTitle =
            By.xpath("//h1[contains(normalize-space(),'Tournament creation')]");

    By tournamentDescription =
            By.xpath("//*[contains(normalize-space(),'Create a private tournament')]");

    By optionsText =
            By.xpath("//*[contains(normalize-space(),'Options')]");

    public CreateTournamentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickCreateTournament() {
        WebElement link = wait.until(
                ExpectedConditions.elementToBeClickable(createTournamentLink)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", link);

        wait.until(ExpectedConditions.urlContains("/en/t/create-tournament"));
    }

    public boolean tournamentCreationPageIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(tournamentCreationTitle)
        ).isDisplayed();
    }

    public boolean tournamentDescriptionIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(tournamentDescription)
        ).isDisplayed();
    }

    public boolean optionsTextIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(optionsText)
        ).isDisplayed();
    }
}