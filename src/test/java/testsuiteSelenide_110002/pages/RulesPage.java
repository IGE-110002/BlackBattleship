package testsuiteSelenide_110002.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.$x;

public class RulesPage {

    private final SelenideElement cookieConsentButton =
            $x("//button[@aria-label='Consent' or normalize-space(.)='Consent']");

    private final SelenideElement rulesTitle =
            $x("//h2[contains(text(),'Rules of Battleship game online')]");

    public void scrollToRules() {

        dismissCookiePopup();

        rulesTitle.scrollIntoView(true);
    }

    public boolean rulesAreDisplayed() {

        return rulesTitle.isDisplayed();
    }

    public void dismissCookiePopup() {

        cookieConsentButton.click();

        sleep(1000);
    }
}