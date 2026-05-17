package TestSuiteSelenide_108419.tests;

import TestSuiteSelenide_108419.pages.StartGameVsRobotPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Selenide acceptance test for starting a guest match against the robot.
 */
public class StartGameVsRobotTest extends BaseSelenideTest {

    @Test
    public void userStoryTest1() {
        StartGameVsRobotPage page =
                new StartGameVsRobotPage()
                        .openPage();

        page.waitForManualCookieDismissal();
        page.startGame("ES_Project");

        Assertions.assertTrue(
                page.isGameStarted(),
                "The guest player should be able to start a match against the robot."
        );
    }
}
