package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.pages.WeaponPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test that selects a special weapon and uses it on an enemy cell automatically.
 */
public class WeaponTest extends BaseTest {

    @Test
    public void selectAndUseSpecialWeaponAutomatically() throws Exception {

        OnlineGamePage game = new OnlineGamePage(driver);
        // Start a robot match as guest. This method exists in your codebase.
        game.startRobotGameAsGuest();

        // wait a bit for game UI to settle
        Thread.sleep(3000);

        WeaponPage wp = new WeaponPage(driver);

        // Select a special weapon (throws if cannot)
        wp.selectAndActivateWeapon();

        // Use it on an enemy cell (throws if cannot)
        wp.useWeaponOnEnemyCell();

        // Wait for effect detection (8s)
        boolean effect = wp.waitForWeaponEffect(8);

        // If effect is not detected: capture diagnostics to help debugging
        if (!effect) {
            // use the WeaponPage diagnostics method present in your code
            wp.captureDiagnostics("weapon");
        }

        Assertions.assertTrue(effect, "Expected to detect weapon effect but did not (see target/debug-screenshots).");
    }
}