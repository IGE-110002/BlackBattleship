package testsuiteSelenide_110772.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.open;

public class MainPageSelenidePage {

    private static final String BASE_URL = "https://papergames.io/en/battleship";

    @Step("Clicar na opção Create tournament")
    public void openCreateTournamentPage() {
        $x("//a[normalize-space()='Create tournament']")
                .shouldBe(Condition.visible)
                .click();

        $x("//h1[contains(normalize-space(),'Tournament creation')]")
                .shouldBe(Condition.visible);
    }

    @Step("Voltar à página inicial do Battleship")
    public void goBackToMainPage() {
        back();

        if (!WebDriverRunner.url().contains("/en/battleship")) {
            open(BASE_URL);
        }

        $x("//*[contains(normalize-space(),'Create tournament')]")
                .shouldBe(Condition.visible);
    }

    @Step("Verificar se a opção Play with a friend é apresentada")
    public boolean playWithFriendOptionIsDisplayed() {
        return $x("//*[contains(normalize-space(),'Play with a friend')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }

    @Step("Verificar se a opção Play vs robot é apresentada")
    public boolean playVsRobotOptionIsDisplayed() {
        return $x("//*[contains(normalize-space(),'Play vs robot')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }

    @Step("Verificar se a opção Create tournament é apresentada")
    public boolean createTournamentOptionIsDisplayed() {
        return $x("//*[contains(normalize-space(),'Create tournament')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }
}