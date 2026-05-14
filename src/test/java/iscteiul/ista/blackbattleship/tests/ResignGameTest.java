package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Test;

/**
 * Test class for resigning a game.
 * Tests the resign functionality by confirming the resign dialog.
 */
public class ResignGameTest extends BaseTest {

   /* @Test
    public void testResignGame() throws InterruptedException {
        // Arrange
        HomePage homePage = new HomePage(driver);
        GamePage gamePage = new GamePage(driver);
        ResignDialog resignDialog = new ResignDialog(driver);

        // Wait for page to fully load
        Thread.sleep(3000);

        homePage.openGameUrl();
        homePage.enterNickname("TestPlayer");
        homePage.startGameVsRobot();

        // Wait for game to initialize
        Thread.sleep(3000);

        // Act
        gamePage.clickResignButton();
        Thread.sleep(1000);
        resignDialog.confirmResign();

        // Wait for resign to complete
        Thread.sleep(2000);

        // Assert - After resigning, the game should end.
        // Test passes if no exceptions are thrown during resign flow.
    }*/
}
