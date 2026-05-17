package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Page Object responsible for resigning from a guest match versus the robot.
 */
public class ResignGameTestPage extends RobotGameBasePage {

    private final By abortGameButton =
            By.xpath("//app-room-bottom-toolbar//button[" +
                    "contains(@class, 'btn-outline-dark') and " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'abort game')]");

    private final By abortConfirmationMessage =
            By.xpath("//*[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " +
                    "'are you sure you want to continue?')]");

    private final By confirmAbortGameButton =
            By.xpath("//button[contains(@class, 'btn-danger') and " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'abort game')]");

    public ResignGameTestPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Opens the resign dialog and confirms the resignation.
     */
    public void resignFromCurrentGame() {
        pauseBeforeAbortClick();
        logAbortButtonDiagnostics();

        WebElement abortButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(abortGameButton)
        );

        saveDebugScreenshot("abort-before-click");
        clickToolbarAbortButton(abortButton);
        pauseAfterAbortClick();
        saveDebugScreenshot("abort-after-click");

        wait.until(driver -> !driver.findElements(confirmAbortGameButton).isEmpty()
                || !driver.findElements(abortConfirmationMessage).isEmpty());

        WebElement confirmButton = wait.until(
                ExpectedConditions.elementToBeClickable(confirmAbortGameButton)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", confirmButton);

        waitUntilBackOnStartScreen();
    }

    /**
     * Checks whether the match has ended after confirming resignation.
     *
     * @return true if the start screen is visible again or the board is no longer visible
     */
    public boolean hasLeftCurrentGame() {
        return hasReturnedToStartScreen() || !isGameBoardVisible();
    }

    private void pauseBeforeAbortClick() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The abort click wait was interrupted.", e);
        }
    }

    private void pauseAfterAbortClick() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The abort confirmation wait was interrupted.", e);
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

    private void logAbortButtonDiagnostics() {
        List<WebElement> buttons =
                driver.findElements(abortGameButton);

        System.out.println("=== Abort Game Debug ===");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Abort button matches: " + buttons.size());

        for (int i = 0; i < buttons.size(); i++) {
            WebElement button = buttons.get(i);
            Rectangle rect = button.getRect();

            System.out.println("Abort button [" + i + "] text: " + button.getText());
            System.out.println("Abort button [" + i + "] displayed: " + button.isDisplayed());
            System.out.println("Abort button [" + i + "] enabled: " + button.isEnabled());
            System.out.println("Abort button [" + i + "] class: " + button.getAttribute("class"));
            System.out.println("Abort button [" + i + "] rect: x=" + rect.getX()
                    + ", y=" + rect.getY()
                    + ", width=" + rect.getWidth()
                    + ", height=" + rect.getHeight());
            System.out.println("Abort button [" + i + "] outerHTML: " + button.getAttribute("outerHTML"));
        }

        if (!buttons.isEmpty()) {
            WebElement firstButton = buttons.get(0);

            Object topElement = ((JavascriptExecutor) driver).executeScript("""
                    const element = arguments[0];
                    const rect = element.getBoundingClientRect();
                    const x = rect.left + rect.width / 2;
                    const y = rect.top + rect.height / 2;
                    const target = document.elementFromPoint(x, y);

                    return target ? target.outerHTML : null;
                    """, firstButton);

            System.out.println("Abort button center target outerHTML: " + topElement);
        }

        System.out.println("========================");
    }

    private void saveDebugScreenshot(String name) {
        try {
            File screenshot =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            Path targetDir =
                    Path.of("target", "debug-screenshots");
            Files.createDirectories(targetDir);

            Path targetFile =
                    targetDir.resolve(name + ".png");

            Files.copy(
                    screenshot.toPath(),
                    targetFile,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("Saved screenshot: " + targetFile.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to save screenshot '" + name + "': " + e.getMessage());
        }
    }
}
