package testsuiteSelenide_110772.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class FriendGameSelenidePage {

    @Step("Clicar na opção Play with a friend")
    public void clickPlayWithFriend() {
        $x("//button[contains(normalize-space(),'Play with a friend')] | //a[contains(normalize-space(),'Play with a friend')]")
                .shouldBe(Condition.visible)
                .click();
    }

    @Step("Verificar se o campo de nickname é apresentado")
    public boolean nicknameFieldIsDisplayed() {
        return $x("//input[@placeholder='Nickname']")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }

    @Step("Inserir nickname do utilizador convidado")
    public void enterNickname(String nickname) {
        $x("//input[@placeholder='Nickname']")
                .shouldBe(Condition.visible)
                .setValue(nickname);
    }

    @Step("Confirmar nickname com Enter")
    public void confirmNickname() {
        $x("//input[@placeholder='Nickname']")
                .shouldBe(Condition.visible)
                .pressEnter();
    }
}