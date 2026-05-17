package testsuiteSelenide_110772.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testsuiteSelenide_110772.pages.LeaderboardSelenidePage;

import static com.codeborne.selenide.Selenide.open;

@Feature("User Story 15 - Consultar ranking diário")
public class LeaderboardSelenideTest extends SelenideBaseTest {

    @Test
    @Description("Valida que um utilizador convidado consegue abrir a janela/lista completa do ranking diário.")
    public void userStory15OpenDailyLeaderboardSelenideTest() {

        open(BASE_URL);
        demoPause();

        LeaderboardSelenidePage leaderboardPage =
                new LeaderboardSelenidePage();

        Assertions.assertTrue(
                leaderboardPage.dailyLeaderboardIsDisplayed()
        );
        demoPause();

        leaderboardPage.clickSeeAllRankings();
        demoPause();

        Assertions.assertTrue(
                leaderboardPage.rankingWindowIsDisplayed()
        );
        demoPause();

        Assertions.assertTrue(
                leaderboardPage.rankaIsDisplayed()
        );
        demoPause();
    }
}