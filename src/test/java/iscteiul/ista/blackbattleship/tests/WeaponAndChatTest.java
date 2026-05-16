package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.pages.WeaponPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Combined test: select+use special weapon, then send a chat message.
 */
public class WeaponAndChatTest extends BaseTest {

    @Test
    public void selectUseWeaponAndSendMessage() throws Exception {

        OnlineGamePage game = new OnlineGamePage(driver);
        game.startRobotGameAsGuest();

        // small static wait to let UI settle; consider replacing with explicit waits later
        Thread.sleep(3000);

        WeaponPage wp = new WeaponPage(driver);

        // Select and activate weapon
        wp.selectAndActivateWeapon();

        // Use on an enemy cell
        wp.useWeaponOnEnemyCell();

        // Wait for evidence weapon effect
        boolean effect = wp.waitForWeaponEffect(10);
        if (!effect) wp.captureDiagnostics("no-weapon-effect");

        // Send chat message
        String msg = "Automated test: weapon used ✅";
        wp.sendChatMessage(msg);

        boolean chatOk = wp.waitForChatMessage(msg, 10);
        if (!chatOk) wp.captureDiagnostics("no-chat-confirm");

        Assertions.assertTrue(effect, "Expected weapon effect but none detected (see target/debug-screenshots).");
        Assertions.assertTrue(chatOk, "Expected chat message visible but not found (see target/debug-screenshots).");
    }
}