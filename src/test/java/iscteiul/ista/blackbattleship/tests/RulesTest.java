package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import iscteiul.ista.blackbattleship.pages.RulesPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

public class RulesTest extends BaseTest {

    @Test
    public void rulesTest() throws InterruptedException {

        RulesPage rulesPage = new RulesPage(driver);

        Thread.sleep(5000);

        rulesPage.scrollToRules();

        Thread.sleep(3000);

        Assertions.assertTrue(rulesPage.rulesAreDisplayed());

        Thread.sleep(2000);
    }
}