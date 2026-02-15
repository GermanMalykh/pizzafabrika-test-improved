package tests_ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import common_configs.Config;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

public class BaseUITest {

    @BeforeAll
    public static void setup() {
        SelenideLogger.addListener("AllureSelenideLogger", new AllureSelenide().screenshots(true));

        Configuration.baseUrl = Config.getProperty("ui.base.uri");
        Configuration.browser = Config.getProperty("browser");
        Configuration.browserSize = Config.getProperty("browser.size");
        Configuration.pageLoadStrategy = "eager";
    }
}
