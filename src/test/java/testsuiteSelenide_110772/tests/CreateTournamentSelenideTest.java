package testsuiteSelenide_110772.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import testsuiteSelenide_110772.pages.CreateTournamentSelenidePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

@Feature("User Story 13 - Criar competição")
public class CreateTournamentSelenideTest extends SelenideBaseTest {

    @Test
    @Description("Valida que um utilizador convidado consegue aceder à página de criação de competição.")
    public void userStory13CreateTournamentSelenideTest() {

        open(BASE_URL);

        CreateTournamentSelenidePage createTournamentPage =
                new CreateTournamentSelenidePage();

        createTournamentPage.clickCreateTournament();

        Assertions.assertTrue(
                createTournamentPage.tournamentCreationPageIsDisplayed()
        );

        Assertions.assertTrue(
                createTournamentPage.tournamentDescriptionIsDisplayed()
        );

        Assertions.assertTrue(
                createTournamentPage.optionsTextIsDisplayed()
        );
    }
}