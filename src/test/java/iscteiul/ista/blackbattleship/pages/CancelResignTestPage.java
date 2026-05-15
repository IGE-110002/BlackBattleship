package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object responsible for canceling a resignation in a guest match versus the robot.
 */
public class CancelResignTestPage extends RobotGameBasePage {

    private final By abortGameButton =
            By.xpath("//app-room-bottom-toolbar//button[" +
                    "contains(@class, 'btn-outline-dark') and " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'abort game')]");

    private final By abortConfirmationMessage =
            By.xpath("//*[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " +
                    "'are you sure you want to continue?')]");

    private final By cancelAbortButton =
            By.xpath("//button[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cancel')]");

    public CancelResignTestPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Opens the resign dialog and cancels the resignation request.
     */
    public void cancelResignationFlow() {
        pauseBeforeAbortClick();

        WebElement abortButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(abortGameButton)
        );

        clickToolbarAbortButton(abortButton);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(abortConfirmationMessage)
        );

        WebElement cancelButton = wait.until(
                ExpectedConditions.elementToBeClickable(cancelAbortButton)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", cancelButton);

        wait.until(driver -> isGameBoardVisible()
                && driver.findElements(abortConfirmationMessage).isEmpty());
    }

    /**
     * Checks whether the match is still in progress after canceling resignation.
     *
     * @return true if the board is visible and the dialog is no longer shown
     */
    public boolean isGameStillRunning() {
        return isGameBoardVisible()
                && driver.findElements(abortConfirmationMessage).isEmpty();
    }

    private void pauseBeforeAbortClick() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The cancel resign wait was interrupted.", e);
        }
    }

    private void clickToolbarAbortButton(WebElement abortButton) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", abortButton);

        try {
            new Actions(driver)
                    .moveToElement(abortButton)
                    .pause(java.time.Duration.ofMillis(300))
                    .click()
                    .perform();
        } catch (Exception ignored) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", abortButton);
        }
    }
}
