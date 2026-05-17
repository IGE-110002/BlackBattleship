package TestSuiteSelenide_108419.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

/**
 * Selenide page object for attacking an enemy cell.
 */
public class AttackEnemyCellPage extends RobotMatchPage {

    private final SelenideElement opponentBoard =
            $x("//*[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), \"your opponent's boats\")]/following::table[1]");

    private final SelenideElement targetEnemyCell =
            $x("//*[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), \"your opponent's boats\")]/following::table[1]//tr[5]/td[5]");

    /**
     * Attacks a visible cell in the enemy board.
     *
     * @return true if the board remains visible after the click
     */
    public boolean attackEnemyCell() {
        opponentBoard.shouldBe(Condition.visible);
        pauseForUi();

        SelenideElement cell = targetEnemyCell.shouldBe(Condition.visible);
        SelenideElement target = firstVisibleChildOrSelf(cell);

        actions()
                .moveToElement(target)
                .click()
                .perform();

        return opponentBoard.is(Condition.visible);
    }
}
