package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;
import pages.GamePage;
import pages.HomePage;

public class StartGameVsRobotTest {

    @Test
    public void testStartGameVsRobot() {
        // Arrange
        HomePage homePage = new HomePage();
        GamePage gamePage = new GamePage();

        // Act
        homePage.openGameUrl();
        homePage.enterNickname("TestPlayer");
        homePage.startGameVsRobot();

        // Assert
        gamePage.verifyGameBoardIsVisible();
    }
}
