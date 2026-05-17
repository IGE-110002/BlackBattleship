package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;

import iscteiul.ista.blackbattleship.pages.EmojiPage;
import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

public class EmojiTest extends BaseTest {

    /**
     * User Story 5:
     * Open emoji menu automatically.
     */
    @Test
    public void openEmojiMenuTest() throws InterruptedException {

        System.out.println(
                "\n📋 TEST 5: Open Emoji Menu Automatically"
        );

        OnlineGamePage onlineGamePage =
                new OnlineGamePage(driver);

        onlineGamePage.startRobotGameAsGuest();

        EmojiPage emojiPage = new EmojiPage(driver);

        emojiPage.openEmojiMenu();

        System.out.println(
                "✅ TEST 5 PASSED: Emoji menu opened successfully\n"
        );

        Thread.sleep(5000);
    }

    /**
     * User Story 6:
     * Send emoji automatically.
     */
    @Test
    public void sendEmojiTest() throws InterruptedException {

        System.out.println(
                "\n📋 TEST 6: Send Emoji Automatically"
        );

        OnlineGamePage onlineGamePage =
                new OnlineGamePage(driver);

        onlineGamePage.startRobotGameAsGuest();

        EmojiPage emojiPage = new EmojiPage(driver);

        emojiPage.openEmojiMenu();

        emojiPage.sendEmoji();

        System.out.println(
                "✅ TEST 6 PASSED: Emoji reaction sent successfully\n"
        );

        Thread.sleep(5000);
    }
}