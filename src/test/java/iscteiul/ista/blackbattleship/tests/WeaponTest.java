package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;

import iscteiul.ista.blackbattleship.pages.WeaponPage;
import iscteiul.ista.blackbattleship.pages.OnlineGamePage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeaponTest extends BaseTest {


    @Test
    public void selectWeaponTest() throws InterruptedException {

        System.out.println("\n📋 TEST 7: Select Special Weapon Automatically");

        OnlineGamePage onlineGamePage = new OnlineGamePage(driver);
        onlineGamePage.startRobotGameAsGuest();

        WeaponPage weaponPage = new WeaponPage(driver);

        // Optional debug
        // weaponPage.captureWeaponDiagnostics();

        weaponPage.selectRocketWeapon();

        System.out.println(
                "✅ TEST 7 PASSED: Special weapon selected successfully\n"
        );

        Thread.sleep(2000);
    }


    @Test
    public void useWeaponTest() throws InterruptedException {

        System.out.println("\n TEST 8: Use Special Weapon Automatically");

        OnlineGamePage onlineGamePage = new OnlineGamePage(driver);
        onlineGamePage.startRobotGameAsGuest();

        WeaponPage weaponPage = new WeaponPage(driver);

        // Optional debug
        // weaponPage.captureWeaponDiagnostics();

        // 1. Select special weapon
        weaponPage.selectRocketWeapon();

        // 2. Use weapon on enemy cell
        weaponPage.useRocketOnEnemyCell();

        // 3. Wait for effect
        boolean effectDetected =
                weaponPage.waitForWeaponEffect(8);

        assertTrue(
                effectDetected,
                "Special weapon effect was not detected."
        );

        System.out.println(
                "✅ TEST 8 PASSED: Special weapon used successfully\n"
        );

        Thread.sleep(10000);
    }
}