package testsuiteSelenide_110002.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class OnlineGamePage {

    private final SelenideElement playOnlineButton =
            $x("//span[contains(text(),'Play online')]");

    private final SelenideElement nicknameField =
            $x("//input[@placeholder='Nickname']");

    private final SelenideElement continueButton =
            $x("//button[contains(text(),'Continue')]");

    private final SelenideElement consentButton =
            $x("//button[@aria-label='Consent' or normalize-space(.)='Consent']");

    public void acceptCookiesIfVisible() {

        sleep(3000);

        if (consentButton.exists()) {

            consentButton.click();

            sleep(1000);
        }
    }

    public void clickPlayOnline() {

        sleep(2000);

        playOnlineButton.click();
    }

    public void enterNickname(String nickname) {

        nicknameField.scrollIntoView(true);

        nicknameField.setValue(nickname);

        sleep(1000);
    }

    public void clickContinue() {

        continueButton.scrollIntoView(true);

        sleep(1000);

        continueButton.click();

        sleep(5000);
    }
}