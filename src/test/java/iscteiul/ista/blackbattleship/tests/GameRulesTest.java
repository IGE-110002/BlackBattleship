package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.GameRulesPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameRulesTest extends BaseTest {

    @Test
    public void userStory15ViewBattleshipRulesTest() {

        GameRulesPage gameRulesPage =
                new GameRulesPage(driver);

        gameRulesPage.scrollToRulesSection();

        Assertions.assertTrue(
                gameRulesPage.rulesTitleIsDisplayed()
        );

        Assertions.assertTrue(
                gameRulesPage.rulesDescriptionIsDisplayed()
        );

        Assertions.assertTrue(
                gameRulesPage.giftsSectionIsDisplayed()
        );

        Assertions.assertTrue(
                gameRulesPage.weaponsSectionIsDisplayed()
        );
    }
}