package testsuiteSelenide_110772.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class CreateTournamentSelenidePage {

    @Step("Clicar na opção Create tournament")
    public void clickCreateTournament() {
        $x("//a[normalize-space()='Create tournament']")
                .shouldBe(Condition.visible)
                .click();
    }

    @Step("Verificar se a página Tournament creation é apresentada")
    public boolean tournamentCreationPageIsDisplayed() {
        return $x("//h1[contains(normalize-space(),'Tournament creation')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }

    @Step("Verificar se a descrição da criação de torneio é apresentada")
    public boolean tournamentDescriptionIsDisplayed() {
        return $x("//*[contains(normalize-space(),'Create a private tournament')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }

    @Step("Verificar se as opções do torneio são apresentadas")
    public boolean optionsTextIsDisplayed() {
        return $x("//*[contains(normalize-space(),'Options')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }
}