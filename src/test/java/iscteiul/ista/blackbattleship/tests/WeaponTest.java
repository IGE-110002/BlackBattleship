package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.pages.WeaponPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeaponTest extends BaseTest {

    /**
     * TEST 1:
     * Select and use special weapon automatically.
     */
    @Order(1)
    @Test
    public void selectAndUseSpecialWeaponAutomatically() throws Exception {

        System.out.println(
                "\n📋 TEST 1: Select And Use Special Weapon Automatically"
        );

        OnlineGamePage game = new OnlineGamePage(driver);

        game.startRobotGameAsGuest();

        Thread.sleep(10000);

        WeaponPage wp = new WeaponPage(driver);

        wp.selectAndActivateWeapon();

        Thread.sleep(1000);

        wp.captureDiagnostics("after-special-weapon-select");

        wp.useWeaponOnEnemyCell();

        Thread.sleep(1000);

        wp.captureDiagnostics("after-enemy-cell-click");

        boolean effect = wp.waitForWeaponEffect(12);

        if (!effect) {
            wp.captureDiagnostics("weapon-effect-not-detected");
        }

        Assertions.assertTrue(
                effect,
                "Expected to detect weapon effect but did not."
        );

        System.out.println(
                "✅ TEST 1 PASSED: Special weapon used successfully\n"
        );
    }

    /**
     * TEST 2:
     * Random weapon attack + hit count + random taunt.
     */
    @Order(2)
    @Test
    public void useWeaponCountHitsAndSendRandomTaunt() throws Exception {

        System.out.println(
                "\n📋 TEST 2: Weapon Count Hits And Send Random Taunt"
        );

        OnlineGamePage game = new OnlineGamePage(driver);

        game.startRobotGameAsGuest();

        Thread.sleep(3000);

        WeaponPage wp = new WeaponPage(driver);

        wp.useWeaponAndSendRandomTaunt();

        System.out.println(
                "✅ TEST 2 PASSED: Extra weapon flow completed successfully\n"
        );
    }

    /**
     * TEST 3:
     * Select weapon and send chat message.
     */
    @Order(3)
    @Test
    public void selectUseWeaponAndSendMessage() throws Exception {

        System.out.println(
                "\n📋 TEST 3: Select Weapon And Send Message"
        );

        OnlineGamePage game = new OnlineGamePage(driver);

        game.startRobotGameAsGuest();

        Thread.sleep(10000);

        WeaponPage wp = new WeaponPage(driver);

        wp.selectAndActivateWeapon();

        wp.useWeaponOnEnemyCell();

        boolean effect = wp.waitForWeaponEffect(10);

        if (!effect) {
            wp.captureDiagnostics("no-weapon-effect");
        }

        String msg = "Automated test: weapon used ✅";

        wp.sendChatMessage(msg);

        boolean chatOk = wp.waitForChatMessage(msg, 10);

        if (!chatOk) {
            wp.captureDiagnostics("no-chat-confirm");
        }

        Assertions.assertTrue(
                effect,
                "Expected weapon effect but none detected."
        );

        Assertions.assertTrue(
                chatOk,
                "Expected chat message visible but not found."
        );

        System.out.println(
                "✅ TEST 3 PASSED: Weapon + chat completed successfully\n"
        );
    }
}