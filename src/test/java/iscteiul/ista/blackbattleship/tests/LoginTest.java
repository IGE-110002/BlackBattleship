package iscteiul.ista.blackbattleship.tests;

import org.junit.jupiter.api.Test;

import iscteiul.ista.blackbattleship.pages.LoginPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest()
            throws InterruptedException {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.openLoginPopup();

        loginPage.enterEmail(
                "project.ssi.worten@gmail.com");

        loginPage.enterPassword(
                "ESPROJECT");

        loginPage.clickPopupLogin();

        Thread.sleep(5000);
    }
}