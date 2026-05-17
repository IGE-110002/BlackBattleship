package iscteiul.ista.blackbattleship.testsuiteSelenide110002.tests;

import iscteiul.ista.blackbattleship.testsuiteSelenide110002.pages.OnlineGamePage;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.sleep;

import static com.codeborne.selenide.Selenide.open;

public class OnlineGameTest {

    @Test
    public void onlineGameTest() {

        open("https://papergames.io/en/battleship");

        sleep(2000);

        OnlineGamePage onlineGamePage = new OnlineGamePage();

        onlineGamePage.acceptCookiesIfVisible();

        onlineGamePage.clickPlayOnline();

        onlineGamePage.enterNickname("ES-Project");

        onlineGamePage.clickContinue();
    }
}