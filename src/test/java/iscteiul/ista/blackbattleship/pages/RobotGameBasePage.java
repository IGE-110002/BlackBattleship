package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Shared support for guest Battleship flows played against the robot.
 */
public abstract class RobotGameBasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private final By startGameOption =
            By.xpath("//*[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'play vs robot')]");

    private final By gameBoard =
            By.xpath("//*[contains(@class, 'board') or contains(@class, 'grid') or contains(@class, 'game')]");

    private final By dialogContainer =
            By.xpath("//*[(@role='dialog' or @aria-modal='true' or contains(@class, 'modal') " +
                    "or contains(@class, 'dialog') or contains(@class, 'popup'))]");

    protected RobotGameBasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Starts a guest match against the robot and waits for the board to become visible.
     *
     * @param nickname guest nickname used in the match
     */
    public void startGuestGame(String nickname) {
        StartGameVsRobotPage startPage =
                new StartGameVsRobotPage(driver);

        startPage.startGameVsRobot(nickname);
        pause(800);
        wait.until(webDriver -> isGameBoardVisible());
    }

    /**
     * Checks whether the game board is still visible.
     *
     * @return true when a visible board is found
     */
    public boolean isGameBoardVisible() {
        return driver.findElements(gameBoard).stream()
                .anyMatch(this::isDisplayed);
    }

    /**
     * Checks whether the initial "Play vs robot" option is visible again.
     *
     * @return true when the user appears to be back on the initial screen
     */
    public boolean hasReturnedToStartScreen() {
        return driver.findElements(startGameOption).stream()
                .anyMatch(this::isDisplayed);
    }

    /**
     * Opens the resign confirmation UI.
     */
    protected void openResignDialog() {
        clickFirstVisible(actionLocator("resign", "give up", "quit"));
        pause(500);
        wait.until(webDriver -> isResignDialogVisible());
    }

    /**
     * Confirms resignation in the currently visible dialog.
     */
    protected void confirmResign() {
        clickFirstVisible(dialogActionLocator("resign", "confirm", "yes", "leave", "quit"));
        pause(800);
    }

    /**
     * Cancels resignation in the currently visible dialog.
     */
    protected void cancelResign() {
        clickFirstVisible(dialogActionLocator("cancel", "continue", "no", "close", "stay"));
        pause(800);
    }

    /**
     * Attempts to attack the first available enemy cell.
     *
     * @return true if a candidate cell was found and clicked
     */
    protected boolean clickFirstUntouchedEnemyCell() {
        wait.until(webDriver -> isGameBoardVisible());
        pause(800);

        Object clicked = ((JavascriptExecutor) driver).executeScript("""
                const isVisible = (element) => {
                    if (!element) {
                        return false;
                    }

                    const style = window.getComputedStyle(element);
                    const rect = element.getBoundingClientRect();

                    return style.display !== 'none'
                        && style.visibility !== 'hidden'
                        && rect.width >= 16
                        && rect.height >= 16
                        && rect.bottom > 0
                        && rect.right > 0;
                };

                const boardSelectors =
                    '[class*="board"], [class*="grid"], [data-testid*="board"], [data-testid*="grid"]';
                const cellSelectors =
                    '[data-row][data-col], [data-x][data-y], [class*="cell"], [class*="square"], ' +
                    '[class*="tile"], button, [role="button"]';

                const boards = [...document.querySelectorAll(boardSelectors)]
                    .filter(isVisible)
                    .filter((board) => board.querySelectorAll(cellSelectors).length >= 20)
                    .sort((left, right) =>
                        left.getBoundingClientRect().left - right.getBoundingClientRect().left
                    );

                const enemyBoard = boards.length > 1 ? boards[boards.length - 1] : boards[0];

                if (!enemyBoard) {
                    return false;
                }

                const alreadyPlayedPattern = /hit|miss|sunk|destroyed|disabled|selected|used/i;
                const cells = [...enemyBoard.querySelectorAll(cellSelectors)]
                    .filter(isVisible)
                    .filter((cell) => {
                        const rect = cell.getBoundingClientRect();
                        const signature =
                            `${cell.className} ${cell.getAttribute('aria-label') || ''} ` +
                            `${cell.getAttribute('data-state') || ''}`;

                        return rect.width <= 80
                            && rect.height <= 80
                            && !alreadyPlayedPattern.test(signature);
                    });

                const target = cells[0];

                if (!target) {
                    return false;
                }

                target.scrollIntoView({ block: 'center', inline: 'center' });
                target.dispatchEvent(new MouseEvent('click', {
                    bubbles: true,
                    cancelable: true,
                    view: window
                }));

                return true;
                """);

        pause(600);
        return Boolean.TRUE.equals(clicked);
    }

    /**
     * Checks whether a confirmation dialog is currently visible.
     *
     * @return true when the resign dialog appears to be open
     */
    protected boolean isResignDialogVisible() {
        return driver.findElements(dialogContainer).stream()
                .anyMatch(this::isDisplayed);
    }

    protected void waitUntilBackOnStartScreen() {
        wait.until(webDriver -> hasReturnedToStartScreen() || !isGameBoardVisible());
    }

    protected void waitUntilGameContinues() {
        wait.until(webDriver -> isGameBoardVisible() && !isResignDialogVisible());
    }

    private By actionLocator(String... keywords) {
        return By.xpath("//*[(self::button or self::a or self::span or self::div) and ("
                + containsAnyKeyword(keywords) + ")]");
    }

    private By dialogActionLocator(String... keywords) {
        return By.xpath("//*[(@role='dialog' or @aria-modal='true' or contains(@class, 'modal') "
                + "or contains(@class, 'dialog') or contains(@class, 'popup'))]//*[(self::button or self::a "
                + "or self::span or self::div) and (" + containsAnyKeyword(keywords) + ")]");
    }

    private String containsAnyKeyword(String... keywords) {
        String normalizedText =
                "translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";

        return Arrays.stream(keywords)
                .map(keyword -> "contains(" + normalizedText + ", '" + keyword.toLowerCase() + "')")
                .collect(Collectors.joining(" or "));
    }

    private void clickFirstVisible(By locator) {
        WebElement element = wait.until(webDriver -> webDriver.findElements(locator).stream()
                .filter(this::isDisplayed)
                .findFirst()
                .orElse(null));

        if (element != null) {
            clickWithJavaScript(element);
        }
    }

    private void clickWithJavaScript(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    private boolean isDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The test execution was interrupted.", e);
        }
    }
}
