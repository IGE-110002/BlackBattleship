package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.EmojiPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Test;

public class EmojiTest extends BaseTest {

    @Test
    public void openEmojiMenuTest() throws InterruptedException {

        Thread.sleep(60000);

        EmojiPage emojiPage = new EmojiPage(driver);

        emojiPage.openEmojiMenu();

        Thread.sleep(5000);
    }
    @Test
    public void sendEmojiTest() throws InterruptedException {
        Thread.sleep(60000);

        EmojiPage emojiPage = new EmojiPage(driver);

        emojiPage.openEmojiMenu();

        emojiPage.sendEmoji();

        Thread.sleep(5000);
    }
}