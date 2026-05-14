package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object responsible for starting a guest Battleship game against the robot.
 */
public class StartGameVsRobotPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By playVsRobotButton =
            By.xpath("//*[contains(translate(normalize-space(), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'play vs robot')]");

    private final By nicknameField =
            By.xpath("//input[@placeholder='Nickname']");

    private final By continueButton =
            By.xpath("//button[contains(translate(normalize-space(), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'continue')]");

    private final By gameBoard =
            By.xpath("//*[contains(@class, 'board') or contains(@class, 'grid') or contains(@class, 'game')]");

    public StartGameVsRobotPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Clicks on the "Play vs robot" option.
     */
    public void clickPlayVsRobot() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(playVsRobotButton)
        );

        clickWithJavaScript(button);
    }

    /**
     * Enters a guest nickname.
     *
     * @param nickname nickname to be used in the game
     */
    public void enterNickname(String nickname) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(nicknameField)
        );

        input.clear();
        input.sendKeys(nickname);
    }

    /**
     * Clicks the continue button after entering the nickname.
     */
    public void clickContinue() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        );

        clickWithJavaScript(button);
    }

    /**
     * Starts a complete guest game against the robot.
     *
     * @param nickname guest nickname
     */
    public void startGameVsRobot(String nickname) {
        clickPlayVsRobot();
        enterNickname(nickname);
        clickContinue();
    }

    /**
     * Checks whether the game appears to have started.
     *
     * @return true if a game area is visible
     */
    public boolean isGameStarted() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(gameBoard)
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void clickWithJavaScript(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
}