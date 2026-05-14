package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Test;

/**
 * Test class for canceling a resignation.
 * Tests the cancel functionality of the resign dialog.
 */
public class CancelResignTest extends BaseTest {

    /*@Test
    public void testCancelResign() throws InterruptedException {
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
        resignDialog.cancelResign();

        // Wait for dialog to close
        Thread.sleep(1000);

        // Assert
        assertTrue(gamePage.isGameBoardVisible(), "Game board should still be visible after canceling resign");
    }*/
}
