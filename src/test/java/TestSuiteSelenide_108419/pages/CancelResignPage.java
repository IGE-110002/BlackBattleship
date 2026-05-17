package TestSuiteSelenide_108419.pages;

import com.codeborne.selenide.Condition;

/**
 * Selenide page object for canceling a resignation request.
 */
public class CancelResignPage extends RobotMatchPage {

    /**
     * Opens the abort confirmation and cancels it.
     */
    public void cancelResignation() {
        pauseForUi();
        clickToolbarAbortButton();
        pauseForPopup();
        waitForAbortConfirmation();
        cancelAbort();
        abortConfirmationMessage.shouldNotBe(Condition.visible);
        gameBoard.shouldBe(Condition.visible);
    }

    /**
     * Checks whether the game is still active after canceling.
     *
     * @return true if the board remains visible and the popup is gone
     */
    public boolean isGameStillRunning() {
        return gameBoard.is(Condition.visible) && !abortConfirmationMessage.is(Condition.visible);
    }
}
