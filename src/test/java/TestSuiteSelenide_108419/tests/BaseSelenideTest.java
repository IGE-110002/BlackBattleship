package TestSuiteSelenide_108419.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Shared Selenide configuration for the acceptance test suite.
 */
public abstract class BaseSelenideTest {

    @BeforeEach
    public void setupSelenide() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--remote-allow-origins=*",
                "--window-size=1920,1080"
        );

        Configuration.browser = "chrome";
        Configuration.baseUrl = "https://papergames.io";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 20000;
        Configuration.pageLoadStrategy = "normal";
        Configuration.headless = true;
        Configuration.browserCapabilities = chromeOptions;

        SelenideLogger.removeListener("AllureSelenide");
        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );
    }

    @AfterEach
    public void tearDownSelenide() {
        Selenide.sleep(5000);
        Selenide.closeWebDriver();
    }
}
