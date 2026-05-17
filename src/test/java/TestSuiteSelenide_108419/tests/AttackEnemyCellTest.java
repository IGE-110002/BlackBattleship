package TestSuiteSelenide_108419.tests;

import TestSuiteSelenide_108419.pages.AttackEnemyCellPage;
import TestSuiteSelenide_108419.pages.StartGameVsRobotPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Selenide acceptance test for attacking an enemy cell.
 */
public class AttackEnemyCellTest extends BaseSelenideTest {

    @Test
    public void userStoryTest2() {
        StartGameVsRobotPage startPage =
                new StartGameVsRobotPage()
                        .openPage();

        startPage.waitForManualCookieDismissal();
        startPage.startGame("ES_Project");

        AttackEnemyCellPage attackPage =
                new AttackEnemyCellPage();

        Assertions.assertTrue(
                attackPage.attackEnemyCell(),
                "The guest player should be able to select an enemy cell to attack."
        );
    }
}
