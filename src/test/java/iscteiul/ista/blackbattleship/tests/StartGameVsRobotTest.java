package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.StartGameVsRobotPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Acceptance test for starting a guest Battleship game against the robot.
 */
public class StartGameVsRobotTest extends BaseTest {

    @Test
    public void testStartGameVsRobot() {

        StartGameVsRobotPage page =
                new StartGameVsRobotPage(driver);

        page.startGameVsRobot("ES-Project");

        Assertions.assertTrue(
                page.isGameStarted(),
                "The game should start after choosing Play vs Robot and entering a nickname."
        );
    }
}