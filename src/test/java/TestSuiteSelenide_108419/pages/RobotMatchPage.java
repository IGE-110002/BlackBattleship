package TestSuiteSelenide_108419.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Shared support for Selenide guest matches against the robot.
 */
public abstract class RobotMatchPage {

    protected final SelenideElement gameBoard =
            $x("//*[contains(@class, 'board') or contains(@class, 'grid') or contains(@class, 'game')]");

    protected final SelenideElement abortGameButton =
            $x("//app-room-bottom-toolbar//button[" +
                    "contains(@class, 'btn-outline-dark') and " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'abort game')]");

    protected final SelenideElement abortConfirmationMessage =
            $x("//*[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " +
                    "'are you sure you want to continue?')]");

    protected final SelenideElement confirmAbortButton =
            $x("//button[contains(@class, 'btn-danger') and " +
                    "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'abort game')]");

    protected final SelenideElement cancelAbortButton =
            $x("//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cancel')]");

    protected final SelenideElement startGameOption =
            $x("//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'play vs robot')]");

    /**
     * Waits until the match board is visible.
     */
    public void waitForGameBoard() {
        gameBoard.shouldBe(Condition.visible);
    }

    /**
     * Checks whether the match board is visible.
     *
     * @return true if the game board remains visible
     */
    public boolean isGameBoardVisible() {
        return gameBoard.is(Condition.visible);
    }

    /**
     * Checks whether the initial start option is visible again.
     *
     * @return true if the user appears back on the initial screen
     */
    public boolean isBackOnStartScreen() {
        return startGameOption.is(Condition.visible);
    }

    /**
     * Clicks the abort button from the match toolbar.
     */
    protected void clickToolbarAbortButton() {
        abortGameButton.shouldBe(Condition.visible);
        abortGameButton.scrollIntoView("{block: 'center'}");

        try {
            actions()
                    .moveToElement(abortGameButton)
                    .pause(java.time.Duration.ofMillis(300))
                    .click()
                    .perform();
        } catch (Exception ignored) {
            executeJavaScript("arguments[0].click();", abortGameButton);
        }
    }

    /**
     * Waits for the abort confirmation popup to become visible.
     */
    protected void waitForAbortConfirmation() {
        abortConfirmationMessage.shouldBe(Condition.visible);
    }

    /**
     * Confirms the abort action.
     */
    protected void confirmAbort() {
        confirmAbortButton.shouldBe(Condition.visible).click();
    }

    /**
     * Cancels the abort action.
     */
    protected void cancelAbort() {
        cancelAbortButton.shouldBe(Condition.visible).click();
    }

    /**
     * Small visual wait to keep the flow human-readable.
     */
    protected void pauseForUi() {
        sleep(5000);
    }

    /**
     * Small wait after clicking the abort button to let the popup animate in.
     */
    protected void pauseForPopup() {
        sleep(1500);
    }

    protected SelenideElement firstVisibleChildOrSelf(SelenideElement cell) {
        SelenideElement child = cell.$("div, span, button");
        return child.exists() ? child : cell;
    }
}
