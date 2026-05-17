package iscteiul.ista.blackbattleship.testsuiteSelenide110002.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private final SelenideElement loginButtonMenu =
            $x("//button[normalize-space()='Login']");

    private final SelenideElement emailField =
            $("#mat-input-serverApp0");

    private final SelenideElement passwordField =
            $("#mat-input-serverApp1");

    private final SelenideElement popupLoginButton =
            $(".w-100:nth-child(1) > .btn-secondary > .front");

    private final SelenideElement cookieConsentButton =
            $x("//button[@aria-label='Consent' or normalize-space(.)='Consent']");

    public void dismissCookiePopupIfPresent() {

        cookieConsentButton.click();

        sleep(1000);
    }

    public void openLoginPopup() {

        dismissCookiePopupIfPresent();

        sleep(2000);

        loginButtonMenu.click();

        sleep(2000);
    }

    public void enterEmail(String email) {

        emailField.setValue(email);
    }

    public void enterPassword(String password) {

        passwordField.setValue(password);

        sleep(1000);
    }

    public void clickPopupLogin() {

        popupLoginButton.scrollIntoView(true);

        sleep(1000);

        popupLoginButton.click();

        sleep(5000);
    }
}