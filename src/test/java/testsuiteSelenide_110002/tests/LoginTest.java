package testsuiteSelenide_110002.tests;

import testsuiteSelenide_110002.pages.LoginPage;
import testsuiteSelenide_110002.utils.BaseTest;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {

        open("https://papergames.io/en/battleship");

        LoginPage loginPage = new LoginPage();

        loginPage.openLoginPopup();

        loginPage.enterEmail(
                "project.ssi.worten@gmail.com");

        loginPage.enterPassword(
                "ESPROJECT");

        loginPage.clickPopupLogin();
    }
}