package TestSuiteSelenide_108419.tests;

import TestSuiteSelenide_108419.pages.ResignGamePage;
import TestSuiteSelenide_108419.pages.StartGameVsRobotPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Selenide acceptance test for resigning from a match.
 */
public class ResignGameTest extends BaseSelenideTest {

    @Test
    public void userStoryTest3() {
        StartGameVsRobotPage startPage =
                new StartGameVsRobotPage()
                        .openPage();

        startPage.waitForManualCookieDismissal();
        startPage.startGame("ES_Project");

        ResignGamePage resignPage =
                new ResignGamePage();

        resignPage.resignFromCurrentGame();

        Assertions.assertTrue(
                resignPage.hasLeftCurrentGame(),
                "The guest player should leave the current game after confirming the abort action."
        );
    }
}
