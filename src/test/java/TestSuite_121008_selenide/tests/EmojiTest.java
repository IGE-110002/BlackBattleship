package TestSuite_121008_selenide.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import iscteiul.ista.blackbattleship.pages.EmojiPage;
import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@Epic("BlackBattleship")
@Feature("Emoji Feature")

public class EmojiTest extends BaseTest {

    /**
     * TEST 5:
     * Open emoji menu automatically.
     */

    @Story("Open Emoji Menu")
    @Description("Verify that emoji menu opens automatically.")
    @Severity(SeverityLevel.NORMAL)

    @Order(1)
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
     * TEST 6:
     * Send emoji automatically.
     */

    @Story("Send Emoji Reaction")
    @Description("Verify that emoji reaction is sent automatically.")
    @Severity(SeverityLevel.CRITICAL)

    @Order(2)
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