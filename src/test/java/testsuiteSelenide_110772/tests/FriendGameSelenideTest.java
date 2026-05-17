package testsuiteSelenide_110772.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testsuiteSelenide_110772.pages.FriendGameSelenidePage;

import static com.codeborne.selenide.Selenide.open;

@Feature("User Story 14 - Criar jogo com amigo")
public class FriendGameSelenideTest extends SelenideBaseTest {

    @Test
    @Description("Valida que um utilizador convidado consegue iniciar o fluxo de jogo com um amigo e inserir nickname.")
    public void userStory14CreateFriendGameSelenideTest() {

        open(BASE_URL);

        FriendGameSelenidePage friendGamePage =
                new FriendGameSelenidePage();

        friendGamePage.clickPlayWithFriend();

        Assertions.assertTrue(
                friendGamePage.nicknameFieldIsDisplayed()
        );

        friendGamePage.enterNickname("Guest110772");

        friendGamePage.confirmNickname();
    }
}