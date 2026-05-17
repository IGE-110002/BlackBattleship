package iscteiul.ista.blackbattleship.testsuiteSelenide110002.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class SettingsPage {

    private final SelenideElement consentButton =
            $x("//button[@aria-label='Consent' or normalize-space(.)='Consent']");

    private final SelenideElement settingsButton =
            $("button[mattooltip='Settings']");

    private final SelenideElement darkModeButton =
            $x("//*[contains(text(),'Dark mode')]");

    private final SelenideElement soundButton =
            $x("//*[contains(text(),'Sound')]");

    private final SelenideElement closeSettingsButton =
            $(".dialog-close .mat-mdc-button-touch-target");

    public void dismissCookiePopup() {

        sleep(3000);

        if (consentButton.exists()) {

            consentButton.click();

            sleep(1000);
        }
    }

    public void openSettings() {

        sleep(2000);

        settingsButton.click();
    }

    public void clickDarkMode() {

        sleep(2000);

        darkModeButton.click();
    }

    public void clickSound() {

        sleep(2000);

        soundButton.click();

        sleep(2000);
    }

    public void closeSettings() {

        sleep(2000);

        closeSettingsButton.click();
    }
}