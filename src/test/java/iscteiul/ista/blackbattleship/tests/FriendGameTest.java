package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.FriendGamePage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FriendGameTest extends BaseTest {

    @Test
    public void userStory14CreateFriendGameTest() {

        FriendGamePage friendGamePage =
                new FriendGamePage(driver);

        friendGamePage.clickPlayWithFriend();

        Assertions.assertTrue(
                friendGamePage.nicknameFieldIsDisplayed()
        );

        friendGamePage.enterNickname("Guest110772");

        friendGamePage.clickContinue();
    }
}