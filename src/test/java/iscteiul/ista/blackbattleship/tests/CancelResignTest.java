package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.CancelResignTestPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for canceling a resignation.
 * TestSuiteSelenide_108419.Tests the cancel functionality of the resign dialog.
 */
public class CancelResignTest extends BaseTest {

    @Test
    public void testCancelResign() {

        CancelResignTestPage page =
                new CancelResignTestPage(driver);

        page.startGuestGame("ES_Project");
        page.cancelResignationFlow();

        Assertions.assertTrue(
                page.isGameStillRunning(),
                "The guest player should remain in the match after canceling resignation."
        );
    }
}
