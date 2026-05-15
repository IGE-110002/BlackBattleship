package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object responsible for attacking an enemy cell during a guest match versus the robot.
 */
public class AttackEnemyCellPage extends RobotGameBasePage {

    private final By opponentBoard =
            By.xpath("//*[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), \"your opponent's boats\")]/following::table[1]");

    private final By targetEnemyCell =
            By.xpath("//*[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), \"your opponent's boats\")]/following::table[1]//tr[5]/td[5]");

    private final By targetEnemyPoint =
            By.xpath("//*[contains(translate(normalize-space(.), " +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), \"your opponent's boats\")]/following::table[1]//tr[5]/td[5]//*[self::div or self::span or self::button][1]");

    public AttackEnemyCellPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Performs a single attack on the enemy board.
     *
     * @return true if a clickable enemy cell was found
     */
    public boolean attackEnemyCell() {
        WebElement board = wait.until(
                ExpectedConditions.visibilityOfElementLocated(opponentBoard)
        );

        pauseBeforeAttack();

        WebElement cell = wait.until(
                ExpectedConditions.elementToBeClickable(targetEnemyCell)
        );

        WebElement clickableTarget = driver.findElements(targetEnemyPoint).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(cell);

        new Actions(driver)
                .moveToElement(clickableTarget)
                .click()
                .perform();

        return board.isDisplayed();
    }

    private void pauseBeforeAttack() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The attack wait was interrupted.", e);
        }
    }
}
