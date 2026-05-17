package testsuiteSelenide_110772.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class LeaderboardSelenidePage {

    @Step("Verificar se a secção Daily leaderboard é apresentada")
    public boolean dailyLeaderboardIsDisplayed() {
        return $x("//*[contains(normalize-space(),'Daily leaderboard')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }

    @Step("Clicar no botão See all do ranking")
    public void clickSeeAllRankings() {
        $x("(//button[contains(normalize-space(),'See all')] | //a[contains(normalize-space(),'See all')])[1]")
                .shouldBe(Condition.visible)
                .scrollIntoView(true)
                .click();
    }

    @Step("Verificar se a janela/lista de rankings é apresentada")
    public boolean rankingWindowIsDisplayed() {
        return $x("//*[contains(normalize-space(),'RANKA') or contains(normalize-space(),'Daily leaderboard')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }

    @Step("Verificar se o texto RANKA é apresentado no ranking")
    public boolean rankaIsDisplayed() {
        return $x("//*[contains(normalize-space(),'RANKA')]")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }
}