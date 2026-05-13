package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;
import pages.GamePage;
import pages.HomePage;

public class AttackEnemyCellTest {

    @Test
    public void testAttackEnemyCell() {
        // Arrange
        HomePage homePage = new HomePage();
        GamePage gamePage = new GamePage();

        homePage.openGameUrl();
        homePage.enterNickname("TestPlayer");
        homePage.startGameVsRobot();

        // Act
        gamePage.attackCell(1, 1);

        // Assert
        gamePage.verifyGameBoardIsVisible();
    }
}
