package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//*[@data-test-marker='Products']")
    public WebElement seeDeveloperToolsButton;

    @FindBy(xpath = "//*[@data-test='suggestion-link']")
    public WebElement findYourToolsButton;

    @FindBy(xpath = "//div[@data-test='main-menu-item' and @data-test-marker = 'Products']")
    public WebElement toolsMenu;

    @FindBy(css = "[data-test='site-header-search-action']")
    public WebElement searchButton;

    By createTournamentLink =
            By.xpath("//a[normalize-space()='Create tournament']");

    By playWithFriendButton =
            By.xpath("//button[contains(normalize-space(),'Play with a friend')] | //a[contains(normalize-space(),'Play with a friend')]");

    By playVsRobotButton =
            By.xpath("//button[contains(normalize-space(),'Play vs robot')] | //a[contains(normalize-space(),'Play vs robot')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void openCreateTournamentPage() {
        WebElement link = wait.until(
                ExpectedConditions.elementToBeClickable(createTournamentLink)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", link);

        wait.until(ExpectedConditions.urlContains("/en/t/create-tournament"));
    }

    public void goBackToMainPage() {
        driver.navigate().back();

        wait.until(ExpectedConditions.urlContains("/en/battleship"));
    }

    public boolean playWithFriendOptionIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(playWithFriendButton)
        ).isDisplayed();
    }

    public boolean playVsRobotOptionIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(playVsRobotButton)
        ).isDisplayed();
    }

    public boolean createTournamentOptionIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(createTournamentLink)
        ).isDisplayed();
    }
}