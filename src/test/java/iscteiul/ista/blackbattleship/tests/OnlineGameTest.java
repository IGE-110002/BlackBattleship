package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;

import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

public class OnlineGameTest extends BaseTest {

    @Test
    public void onlineGameTest()
            throws InterruptedException {

        OnlineGamePage onlineGamePage =
                new OnlineGamePage(driver);

        onlineGamePage.clickPlayOnline();

        onlineGamePage.enterNickname("ES-Project");

        onlineGamePage.clickContinue();

        Thread.sleep(15000);
    }
}