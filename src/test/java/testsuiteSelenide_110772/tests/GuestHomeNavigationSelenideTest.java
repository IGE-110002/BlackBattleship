package testsuiteSelenide_110772.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testsuiteSelenide_110772.pages.MainPageSelenidePage;

import static com.codeborne.selenide.Selenide.open;

@Feature("User Story 16 - Voltar à página inicial")
public class GuestHomeNavigationSelenideTest extends SelenideBaseTest {

    @Test
    @Description("Valida que um utilizador convidado consegue regressar à página inicial e ver os modos de jogo disponíveis.")
    public void userStory16ReturnToHomeAndViewGameModesSelenideTest() {

        open(BASE_URL);
        demoPause();

        MainPageSelenidePage mainPage =
                new MainPageSelenidePage();

        mainPage.openCreateTournamentPage();
        demoPause();

        mainPage.goBackToMainPage();
        demoPause();

        Assertions.assertTrue(
                mainPage.playWithFriendOptionIsDisplayed()
        );
        demoPause();

        Assertions.assertTrue(
                mainPage.playVsRobotOptionIsDisplayed()
        );
        demoPause();

        Assertions.assertTrue(
                mainPage.createTournamentOptionIsDisplayed()
        );
        demoPause();
    }
}