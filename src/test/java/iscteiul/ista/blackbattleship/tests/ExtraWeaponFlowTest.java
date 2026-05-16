package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.pages.WeaponPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Test;

/**
 * Extra feature test:
 * Random weapon attack + hit count + random taunt message.
 */
public class ExtraWeaponFlowTest extends BaseTest {

    @Test
    public void useWeaponCountHitsAndSendRandomTaunt() throws Exception {

        OnlineGamePage game = new OnlineGamePage(driver);
        game.startRobotGameAsGuest();

        Thread.sleep(3000);

        WeaponPage wp = new WeaponPage(driver);

        wp.useWeaponAndSendRandomTaunt();
    }
}