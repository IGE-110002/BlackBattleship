package iscteiul.ista.blackbattleship.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach // Elie Kalumba
    public void setup() throws InterruptedException {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("https://papergames.io/en/battleship");

        // Kalumba
        Thread.sleep(5000);

        try {

            driver.findElement(
                    By.xpath("//button[contains(text(),'Consent')]")
            ).click();

            System.out.println("Cookies accepted");

            Thread.sleep(3000);

        } catch (Exception e) {

            System.out.println("Cookies popup not found");
        }

        try {

            driver.findElement(
                    By.xpath("//*[contains(text(),'Play vs Robot')]")
            ).click();

            System.out.println("Game started");

            Thread.sleep(5000);

        } catch (Exception e) {

            System.out.println("Play vs Robot button not found");
        }
    }

    @AfterEach
    public void tearDown() throws InterruptedException {

        Thread.sleep(10000);

        driver.quit();
    }
}