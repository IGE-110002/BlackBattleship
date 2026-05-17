package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;

import iscteiul.ista.blackbattleship.pages.SettingsPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

public class SettingsTest extends BaseTest {

    @Test
    public void settingsTest()
            throws InterruptedException {

        SettingsPage settingsPage =
                new SettingsPage(driver);

        settingsPage.openSettings();

        settingsPage.clickDarkMode();

       // settingsPage.clickSound();

        settingsPage.closeSettings();

        Thread.sleep(2000);
    }
}