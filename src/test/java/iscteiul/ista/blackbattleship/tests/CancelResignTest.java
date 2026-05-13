package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;
import pages.GamePage;
import pages.HomePage;
import pages.ResignDialog;

public class CancelResignTest {

    @Test
    public void testCancelResign() {
        // Arrange
        HomePage homePage = new HomePage();
        GamePage gamePage = new GamePage();
        ResignDialog resignDialog = new ResignDialog();

        homePage.openGameUrl();
        homePage.enterNickname("TestPlayer");
        homePage.startGameVsRobot();

        // Act
        gamePage.clickResign();
        resignDialog.cancelResign();

        // Assert
        gamePage.verifyGameBoardIsVisible();
    }
}
