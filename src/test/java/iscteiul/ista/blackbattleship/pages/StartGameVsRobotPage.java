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

/**
 * Page Object responsible for starting a guest Battleship game against the robot.
 */
public class StartGameVsRobotPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By playVsRobotButton =
            By.xpath("//button[" +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'play vs robot')" +
                    "]");

    private final By nicknameField =
            By.xpath("//form//input[not(@type='hidden')]");

    private final By continueButton =
            By.xpath("//form//button[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'continue')]");

    private final By gameBoard =
            By.xpath("//*[contains(@class, 'board') or contains(@class, 'grid') or contains(@class, 'game')]");

    private final By abortGameButton =
            By.xpath("//*[(self::button or self::a or self::span or self::div) and " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " +
                    "'abort game')]");

    private final By abortGamePopup =
            By.xpath("//*[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " +
                    "'are you sure you want to continue?')]");

    private final By abortGamePopupButton =
            By.xpath("//button[" +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " +
                    "'abort game')]");

    private final By cookieConsentButton =
            By.xpath("//button[" +
                    "@aria-label='Consent' or normalize-space(.)='Consent'" +
                    "]");

    public StartGameVsRobotPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Clicks on the "Play vs robot" option.
     */
    public void clickPlayVsRobot() {
        dismissCookiePopupIfPresent();

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
        dismissCookiePopupIfPresent();

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
        dismissCookiePopupIfPresent();

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
        waitForManualPopupClose();
        clickPlayVsRobot();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nicknameField)
        );
        enterNickname(nickname);
        clickContinue();
    }

    /**
     * Clicks the "Abort game" action during an active match.
     */
    public void clickAbortGame() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(abortGameButton)
        );

        clickWithJavaScript(button);
    }

    /**
     * Confirms the "Abort game" action in the popup dialog.
     */
    public void confirmAbortGame() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(abortGamePopup)
        );

        WebElement button = wait.until(driver -> driver.findElements(abortGamePopupButton).stream()
                .filter(WebElement::isDisplayed)
                .reduce((first, second) -> second)
                .orElse(null));

        clickWithJavaScript(button);
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

    /**
     * Checks whether the test user has returned to the initial screen after aborting the game.
     *
     * @return true if the "Play vs robot" option becomes visible again
     */
    public boolean isBackOnStartScreen() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(playVsRobotButton)
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

    private void dismissCookiePopupIfPresent() {
        try {
            WebElement button = new WebDriverWait(driver, Duration.ofSeconds(3)).until(
                    ExpectedConditions.presenceOfElementLocated(cookieConsentButton)
            );

            if (button.isDisplayed()) {
                clickWithJavaScript(button);
            }
        } catch (TimeoutException | NoSuchElementException ignored) {
            // Cookie banner is optional, so the test should continue when it is not present.
        }
    }

    private void waitForManualPopupClose() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The manual popup wait was interrupted.", e);
        }
    }
}
