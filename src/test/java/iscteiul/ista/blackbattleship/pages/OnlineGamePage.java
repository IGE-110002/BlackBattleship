package iscteiul.ista.blackbattleship.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OnlineGamePage {

    WebDriver driver;

    By playOnlineButton =
            By.xpath("//span[contains(text(),'Play online')]");

    By playVsRobotButton =
            By.xpath("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'play vs robot')]");

    By nicknameField =
            By.xpath("//input[@placeholder='Nickname']");

    By continueButton =
            By.xpath("//button[contains(text(),'Continue')]");

    By consentButton =
            By.xpath("//button[contains(text(),'Consent')]");

    public OnlineGamePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickPlayOnline() throws InterruptedException {

        Thread.sleep(3000);

        WebElement playBtn =
                driver.findElement(playOnlineButton);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", playBtn);
    }

    public void clickPlayVsRobot() throws InterruptedException {

        Thread.sleep(3000);

        WebElement playRobot =
                driver.findElement(playVsRobotButton);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", playRobot);

        System.out.println("Play vs robot clicked");
    }

    public void acceptCookiesIfVisible() throws InterruptedException {

        Thread.sleep(3000);

        try {
            WebElement consent =
                    driver.findElement(consentButton);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", consent);

            System.out.println("Cookies accepted");

        } catch (Exception e) {
            System.out.println("Cookies popup not found");
        }
    }

    public void enterNickname(String nickname)
            throws InterruptedException {

        Thread.sleep(3000);

        WebElement input =
                driver.findElement(nicknameField);

        ((JavascriptExecutor) driver).executeScript("""
            const input = arguments[0];
            const value = arguments[1];

            input.scrollIntoView({block: 'center'});
            input.focus();
            input.value = value;

            input.dispatchEvent(new Event('input', { bubbles: true }));
            input.dispatchEvent(new Event('change', { bubbles: true }));
            input.dispatchEvent(new KeyboardEvent('keyup', { bubbles: true }));
        """, input, nickname);

        System.out.println("Nickname entered");
    }

    public void clickContinue()
            throws InterruptedException {

        Thread.sleep(2000);

        WebElement continueBtn =
                driver.findElement(continueButton);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", continueBtn);

        Thread.sleep(1000);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", continueBtn);

        System.out.println("Continue clicked");
    }

    public void startRobotGameAsGuest()
            throws InterruptedException {

        acceptCookiesIfVisible();

        clickPlayVsRobot();

        enterNickname("ES-Project");

        clickContinue();

        Thread.sleep(7000);

        System.out.println("Robot game started automatically");
    }
}