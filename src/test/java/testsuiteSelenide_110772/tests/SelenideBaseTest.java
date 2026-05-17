package testsuiteSelenide_110772.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class SelenideBaseTest {

    protected static final String BASE_URL = "https://papergames.io/en/battleship";

    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.browserSize = "1552x840";

        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}