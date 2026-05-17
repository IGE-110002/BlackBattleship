package testsuiteSelenide_110002.tests;

import testsuiteSelenide_110002.pages.SettingsPage;
import testsuiteSelenide_110002.utils.BaseTest;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class SettingsTest extends BaseTest {

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