package iscteiul.ista.blackbattleship.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://papergames.io/en/battleship");
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}