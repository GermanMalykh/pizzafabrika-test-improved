import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.ScrollIntoViewOptions.Block.center;
import static com.codeborne.selenide.ScrollIntoViewOptions.instant;
import static com.codeborne.selenide.Selenide.*;

public class CityTest {
    private String cityName = "Вологда";
    private String altCityName = "Череповец";
    private String pizzaName = "Мясное плато";
    private String largeSizeText = "Большая";
    private String takeawayTabText = "С собой";
    private String deliveryTabText = "Доставка";
    private String addToCartButtonText = "В корзину";
    private String selfDeliveryAddress = "Ресторан, Пошехонское шоссе, 10";

    @Test
    @DisplayName("Сохранение состояния корзины при смене города")
    void keepCartWhenCityChangesTest() {
        Configuration.baseUrl = "https://pizzafabrika.ru";
        open("/");

        $$("[class*='list_container'] button")
                .findBy(text(cityName))
                .shouldBe(visible)
                .click();

        $("button [class*='current-user-location']").shouldHave(text(cityName));

        refresh();

        $("button [class*='current-user-location']").shouldHave(text(cityName));

        $$("[class*='switcher_tab'] span")
                .findBy(text(deliveryTabText))
                .shouldBe(visible).click();

        $("[data-testid='delivery-logic_unknown-card']").click();

        $("[class*='polygon-info_title']").shouldHave(text(cityName));

        $("[class*='back-button']").click();

        $$("[class*='switcher_tab'] span")
                .findBy(text(takeawayTabText))
                .shouldBe(visible).click();

        $("[class*='selfdelivery-districts-modal'] h3").shouldHave(text(cityName));

        $$("[class*='list_container'] li [class*='radioButton_label-block']").findBy(text(selfDeliveryAddress)).shouldBe(visible).click();

        $("[data-testid='pizza']").click();

        $$("#pizza article[role='button']")
                .findBy(text(pizzaName))
                .scrollIntoView((instant().block(center)))
                .shouldBe(visible).click();

        $$("[class*='switcher_tab-label']")
                .findBy(text(largeSizeText))
                .shouldBe(visible).click();

        $$("[class*='editor-buttons_editor']")
                .findBy(text(addToCartButtonText))
                .shouldBe(visible).click();

        actions().sendKeys(Keys.ESCAPE).perform();

        $("button [class*='current-user-location']").click();

        $$("[class*='list_container'] button")
                .findBy(text(altCityName))
                .shouldBe(visible)
                .click();

        $("[class*='entry-card_info']").shouldHave(text(pizzaName + " " + largeSizeText));
    }
}