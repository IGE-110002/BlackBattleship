package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;

import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.pages.WeaponPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

public class WeaponTest extends BaseTest {

    @Test
    public void selectWeaponTest()
            throws InterruptedException {

        OnlineGamePage onlineGamePage =
                new OnlineGamePage(driver);

        onlineGamePage.startRobotGameAsGuest();

        WeaponPage weaponPage =
                new WeaponPage(driver);

        weaponPage.selectRocketWeapon();

        Thread.sleep(5000);
    }

    @Test
    public void useWeaponTest()
            throws InterruptedException {

        OnlineGamePage onlineGamePage =
                new OnlineGamePage(driver);

        onlineGamePage.startRobotGameAsGuest();

        WeaponPage weaponPage =
                new WeaponPage(driver);

        weaponPage.selectRocketWeapon();

        weaponPage.useRocketOnEnemyCell();

        Thread.sleep(5000);
    }
}