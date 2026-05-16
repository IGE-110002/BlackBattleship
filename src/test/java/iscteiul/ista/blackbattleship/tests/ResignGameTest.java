package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.ResignGameTestPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for resigning a game.
 * Tests the resign functionality by confirming the resign dialog.
 */
public class ResignGameTest extends BaseTest {

    @Test
    public void testResignGame() {

        ResignGameTestPage page =
                new ResignGameTestPage(driver);

        page.startGuestGame("ES_Project");
        page.resignFromCurrentGame();

        Assertions.assertTrue(
                page.hasLeftCurrentGame(),
                "The guest player should leave the current match after confirming resignation."
        );
    }
}
