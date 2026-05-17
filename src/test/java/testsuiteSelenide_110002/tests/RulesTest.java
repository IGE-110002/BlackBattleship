package testsuiteSelenide_110002.tests;

import testsuiteSelenide_110002.pages.RulesPage;
import testsuiteSelenide_110002.utils.BaseTest;
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