package testsuiteSelenide_110002.utils;

import org.junit.jupiter.api.AfterEach;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        closeWebDriver();
    }
}