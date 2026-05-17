package TestSuite_121008_selenide.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Setup chromedriver binary automatically
        WebDriverManager.chromedriver().setup();

        ChromeOptions opts = new ChromeOptions();
        // optionally run headless for CI:
        // opts.addArguments("--headless=new");
        opts.addArguments("--disable-gpu");
        opts.addArguments("--window-size=1280,900");
        driver = new ChromeDriver(opts);
        driver.manage().window().maximize();
        driver.get("https://papergames.io/en/battleship");
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        if (driver != null) driver.quit();
    }
}