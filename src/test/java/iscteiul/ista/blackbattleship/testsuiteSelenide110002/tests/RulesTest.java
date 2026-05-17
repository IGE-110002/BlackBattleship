package iscteiul.ista.blackbattleship.testsuiteSelenide110002.tests;

import iscteiul.ista.blackbattleship.testsuiteSelenide110002.pages.RulesPage;
import iscteiul.ista.blackbattleship.testsuiteSelenide110002.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class RulesTest extends BaseTest {

    @Test
    public void rulesTest() {

        open("https://papergames.io/en/battleship");

        RulesPage rulesPage = new RulesPage();

        rulesPage.scrollToRules();

        sleep(5000);

        Assertions.assertTrue(rulesPage.rulesAreDisplayed());
    }
}