package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.LeaderboardPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LeaderboardTest extends BaseTest {

    @Test
    public void userStory15OpenDailyLeaderboardRankingWindowTest() {

        LeaderboardPage leaderboardPage =
                new LeaderboardPage(driver);

        Assertions.assertTrue(
                leaderboardPage.dailyLeaderboardIsDisplayed()
        );

        leaderboardPage.clickSeeAllRankings();

        Assertions.assertTrue(
                leaderboardPage.rankingWindowIsDisplayed()
        );

        Assertions.assertTrue(
                leaderboardPage.generalRankingIsDisplayed()
        );
    }
}