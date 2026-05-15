package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;

import iscteiul.ista.blackbattleship.pages.EmojiPage;
import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

public class EmojiTest extends BaseTest {

    @Test
    public void openEmojiMenuTest()
            throws InterruptedException {

        OnlineGamePage onlineGamePage =
                new OnlineGamePage(driver);

        onlineGamePage.startRobotGameAsGuest();

        EmojiPage emojiPage =
                new EmojiPage(driver);

        emojiPage.openEmojiMenu();

        Thread.sleep(5000);
    }

    @Test
    public void sendEmojiTest()
            throws InterruptedException {

        OnlineGamePage onlineGamePage =
                new OnlineGamePage(driver);

        onlineGamePage.startRobotGameAsGuest();

        EmojiPage emojiPage =
                new EmojiPage(driver);

        emojiPage.openEmojiMenu();

        emojiPage.sendEmoji();

        Thread.sleep(5000);
    }
}