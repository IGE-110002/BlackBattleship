package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class WeaponPage {

    WebDriver driver;

    public WeaponPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectRocketWeapon()
            throws InterruptedException {

        Thread.sleep(3000);

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript("""
            let weapon = document.elementFromPoint(
                window.innerWidth / 2 + 105,
                window.innerHeight - 230
            );

            if (!weapon) {
                throw new Error('Rocket weapon not found.');
            }

            if (typeof weapon.click !== 'function') {
                weapon = weapon.closest('button, div, li, span');
            }

            if (!weapon || typeof weapon.click !== 'function') {
                throw new Error('Clickable rocket weapon not found.');
            }

            weapon.click();
        """);

        System.out.println("Rocket weapon selected");

        Thread.sleep(2000);
    }

    public void useRocketOnEnemyCell()
            throws InterruptedException {

        Thread.sleep(3000);

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript("""
            let enemyCell = document.elementFromPoint(
                window.innerWidth / 2 + 190,
                window.innerHeight / 2 - 70
            );

            if (!enemyCell) {
                throw new Error('Enemy cell not found.');
            }

            if (typeof enemyCell.click !== 'function') {
                enemyCell = enemyCell.closest(
                    'button, div, li, span, canvas'
                );
            }

            if (!enemyCell ||
                typeof enemyCell.click !== 'function') {

                throw new Error(
                    'Clickable enemy cell not found.'
                );
            }

            enemyCell.click();
        """);

        System.out.println(
                "Rocket weapon used on enemy cell");

        Thread.sleep(2000);
    }
}