package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class GamePage {

    private final SelenideElement gameBoard = $("#game-board");
    private final SelenideElement resignButton = $("#resign");

    public void attackCell(int row, int col) {
        SelenideElement cell = $("[data-row='" + row + "'][data-col='" + col + "']");
        cell.click();
    }

    public void clickResign() {
        resignButton.click();
    }

    public void verifyGameBoardIsVisible() {
        gameBoard.shouldBe(Condition.visible);
    }
}
