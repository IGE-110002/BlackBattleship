package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.RobotGamePage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RobotGameTest extends BaseTest {

    @Test
    public void userStory15PlayVsRobotWithNicknameTest() {

        RobotGamePage robotGamePage =
                new RobotGamePage(driver);

        robotGamePage.clickPlayVsRobot();

        Assertions.assertTrue(
                robotGamePage.nicknameFieldIsDisplayed()
        );

        robotGamePage.enterNickname("Guest110772");

        robotGamePage.clickContinue();
    }
}