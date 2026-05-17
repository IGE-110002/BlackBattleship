package TestSuiteSelenide_108419.pages;

import com.codeborne.selenide.Condition;

/**
 * Selenide page object for resigning from an active match.
 */
public class ResignGamePage extends RobotMatchPage {

    /**
     * Aborts the current game and confirms the popup.
     */
    public void resignFromCurrentGame() {
        pauseForUi();
        clickToolbarAbortButton();
        pauseForPopup();
        waitForAbortConfirmation();
        confirmAbort();
        startGameOption.shouldBe(Condition.visible);
    }

    /**
     * Checks whether the user left the current game.
     *
     * @return true if the initial screen is visible again
     */
    public boolean hasLeftCurrentGame() {
        return isBackOnStartScreen() || !isGameBoardVisible();
    }
}
