package iscteiul.ista.blackbattleship.pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class EmojiPage {

    WebDriver driver;

    public EmojiPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openEmojiMenu() throws InterruptedException {

        Thread.sleep(3000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("""
            const button = document.elementFromPoint(
                window.innerWidth - 95,
                window.innerHeight - 165
            );

            if (!button) {
                throw new Error('Emoji button position not found.');
            }

            button.click();
        """);

        System.out.println("Emoji menu opened");

        Thread.sleep(2000);
    }
    public void sendEmoji() throws InterruptedException {

        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("""
        const emojiToClick = document.elementFromPoint(
            window.innerWidth - 245,
            window.innerHeight - 185
        );

        if (!emojiToClick) {
            throw new Error('Emoji option not found');
        }

        emojiToClick.click();
    """);

        System.out.println("Emoji sent");

        Thread.sleep(2000);
    }
}