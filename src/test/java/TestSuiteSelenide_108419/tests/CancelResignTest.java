package TestSuiteSelenide_108419.tests;

import TestSuiteSelenide_108419.pages.CancelResignPage;
import TestSuiteSelenide_108419.pages.StartGameVsRobotPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Selenide acceptance test for canceling the abort action.
 */
public class CancelResignTest extends BaseSelenideTest {

    @Test
    public void userStoryTest4() {
        StartGameVsRobotPage startPage =
                new StartGameVsRobotPage()
                        .openPage();

        startPage.waitForManualCookieDismissal();
        startPage.startGame("ES_Project");

        CancelResignPage cancelPage =
                new CancelResignPage();

        cancelPage.cancelResignation();

        Assertions.assertTrue(
                cancelPage.isGameStillRunning(),
                "The guest player should continue the match after canceling the abort action."
        );
    }
}
