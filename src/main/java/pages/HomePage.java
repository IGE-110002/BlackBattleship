package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    private final SelenideElement nicknameField = $("#nickname");
    private final SelenideElement playRobotButton = $("#play-robot");

    public void openGameUrl() {
        open("https://YOUR_GAME_URL");
    }

    public void enterNickname(String nickname) {
        nicknameField.setValue(nickname);
    }

    public void startGameVsRobot() {
        playRobotButton.click();
    }
}
