package iscteiul.ista.blackbattleship.tests;

import iscteiul.ista.blackbattleship.pages.CreateTournamentPage;
import iscteiul.ista.blackbattleship.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateTournamentTest extends BaseTest {

    @Test
    public void userStory13CreateTournamentTest() {

        CreateTournamentPage createTournamentPage =
                new CreateTournamentPage(driver);

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