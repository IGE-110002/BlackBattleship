package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.MainPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GuestHomeNavigationTest extends BaseTest {

    @Test
    public void userStory16ReturnToHomePageAndViewGameModesTest() {

        MainPage mainPage = new MainPage(driver);

        mainPage.openCreateTournamentPage();

        mainPage.goBackToMainPage();

        Assertions.assertTrue(
                mainPage.playWithFriendOptionIsDisplayed()
        );

        Assertions.assertTrue(
                mainPage.playVsRobotOptionIsDisplayed()
        );

        Assertions.assertTrue(
                mainPage.createTournamentOptionIsDisplayed()
        );
    }
}