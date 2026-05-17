package TestSuiteSelenide_108419.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Selenide page object for starting a guest match against the robot.
 */
public class StartGameVsRobotPage extends RobotMatchPage {

    private final SelenideElement playVsRobotButton =
            $x("//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'play vs robot')]");

    private final SelenideElement nicknameField =
            $x("//form//input[not(@type='hidden')]");

    private final SelenideElement continueButton =
            $x("//form//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'continue')]");

    private final SelenideElement cookieConsentButton =
            $x("//button[@aria-label='Consent' or normalize-space(.)='Consent']");

    /**
     * Opens the Battleship landing page.
     *
     * @return the current page object
     */
    public StartGameVsRobotPage openPage() {
        open("https://papergames.io/en/battleship");
        return page(this);
    }

    /**
     * Gives time to close any initial popup manually.
     */
    public void waitForManualCookieDismissal() {
        sleep(5000);
    }

    /**
     * Starts a guest match against the robot.
     *
     * @param nickname guest nickname
     */
    public void startGame(String nickname) {
        dismissCookiePopupIfPresent();
        playVsRobotButton.shouldBe(Condition.visible).click();
        nicknameField.shouldBe(Condition.visible).setValue(nickname);
        continueButton.shouldBe(Condition.visible).click();
    }

    /**
     * Checks whether the game started.
     *
     * @return true if the game board is visible
     */
    public boolean isGameStarted() {
        return gameBoard.shouldBe(Condition.visible).isDisplayed();
    }

    private void dismissCookiePopupIfPresent() {
        if (cookieConsentButton.exists() && cookieConsentButton.isDisplayed()) {
            cookieConsentButton.click();
        }
    }
}
