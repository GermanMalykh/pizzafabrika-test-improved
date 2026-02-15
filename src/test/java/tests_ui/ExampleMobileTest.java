package tests_ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import drivers.MobileDriver;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static io.appium.java_client.AppiumBy.id;
import static io.qameta.allure.Allure.*;

public class ExampleMobileTest extends MobileDriver {
    private final String APP_ID = "com.example.kaspresso:id/";

    private final SelenideAppiumElement
            navigateToPetStore = $(id(APP_ID + "btnPets")),
            createPetBtn = $(id(APP_ID + "createPet")),
            petNameInput = $(id(APP_ID + "createName")),
            petPhotoUrlInput = $(id(APP_ID + "createPhotoUrl")),
            petCreate = $(id(APP_ID + "btnCreate")),
            petEvetsText = $(id(APP_ID + "eventText"));

    @BeforeAll
    static void setupAllure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true));
    }

    @BeforeEach
    void beforeEachSetup() {
        Configuration.browser = MobileDriver.class.getName();
        Configuration.browserSize = null;

        open();
    }

    @Test
    @DisplayName("Example Mobile CI/CD test")
    void createPetExampleTest() {
        step("Открываем раздел Pet Store", () -> {
            navigateToPetStore.click();
        });
        step("Нажимаем кнопку создания питомца", () -> {
            createPetBtn.click();
        });
        step("Вводим имя питомца PetName", () -> {
            petNameInput.setValue("PetName");
        });
        step("Вводим URL фото питомца", () -> {
            petPhotoUrlInput.setValue("https://petstore.swagger.io/images/petstore-logo.png");
        });
        step("Создаём питомца", () -> {
            petCreate.click();
        });
        step("Проверяем, что питомец создан: PetName", () -> {
            petEvetsText.shouldHave(Condition.text("Создан питомец: PetName"));
        });
    }
}
