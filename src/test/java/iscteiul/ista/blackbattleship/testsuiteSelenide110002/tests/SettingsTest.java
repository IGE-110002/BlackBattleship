package iscteiul.ista.blackbattleship.testsuiteSelenide110002.tests;

import iscteiul.ista.blackbattleship.testsuiteSelenide110002.pages.SettingsPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class SettingsTest {

    @Test
    public void settingsTest() {

        open("https://papergames.io/en/battleship");

        SettingsPage settingsPage = new SettingsPage();

        settingsPage.dismissCookiePopup();

        settingsPage.openSettings();

        settingsPage.clickDarkMode();

      //  settingsPage.clickSound();

        settingsPage.closeSettings();
    }
}