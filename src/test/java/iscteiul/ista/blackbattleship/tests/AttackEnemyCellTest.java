package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.AttackEnemyCellPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for attacking an enemy cell.
 * Tests the attack functionality during gameplay.
 */
public class AttackEnemyCellTest extends BaseTest {

    @Test
    public void testAttackEnemyCell() {

        AttackEnemyCellPage page =
                new AttackEnemyCellPage(driver);

        page.startGuestGame("ES-Project");

        Assertions.assertTrue(
                page.attackEnemyCell(),
                "The guest player should be able to select an enemy cell to attack."
        );

        Assertions.assertTrue(
                page.isGameBoardVisible(),
                "The game board should remain visible after attacking an enemy cell."
        );
    }
}
