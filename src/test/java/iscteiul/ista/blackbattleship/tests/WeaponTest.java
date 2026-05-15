package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.WeaponPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Test;

public class WeaponTest extends BaseTest {

    @Test
    public void selectWeaponTest()
            throws InterruptedException {

        WeaponPage weaponPage =
                new WeaponPage(driver);

        weaponPage.selectRocketWeapon();

        Thread.sleep(5000);
    }

    @Test
    public void useWeaponTest()
            throws InterruptedException {

        WeaponPage weaponPage =
                new WeaponPage(driver);

        weaponPage.selectRocketWeapon();

        Thread.sleep(2000);

        weaponPage.useRocketOnEnemyCell();

        Thread.sleep(5000);
    }
}