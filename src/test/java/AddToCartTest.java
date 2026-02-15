import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.ScrollIntoViewOptions.Block.center;
import static com.codeborne.selenide.ScrollIntoViewOptions.instant;
import static com.codeborne.selenide.Selenide.*;

public class AddToCartTest {
    private String pizzaName = "Мясное плато";
    private String toppingName = "Шампиньоны 20г";
    private String addToCartButtonText = "В корзину";
    private String largeSizeText = "Большая";
    private String mediumSizeText = "Средняя";
    private String cartItemCountText = "В корзине 1 шт.";

    @Test
    @DisplayName("Добавление товара в корзину")
    void addPizzaToCartTest() {
        Configuration.baseUrl = "https://pizzafabrika.ru";
        open("/vologda");

        $("[data-testid='pizza']").click();

        $$("#pizza article[role='button']")
                .findBy(text(pizzaName))
                .scrollIntoView((instant().block(center)))
                .shouldBe(visible).click();

        $$("[class*='switcher_tab-label']")
                .findBy(text(largeSizeText))
                .shouldBe(visible).click();

        int largeSizePrice = Integer.parseInt($$("button").findBy(text(addToCartButtonText)).$("[class*='currency_nowrap'] span").shouldNotBe(animated).getText());

        $$("[class*='switcher_tab-label']")
                .findBy(text(mediumSizeText))
                .shouldBe(visible).click();

        int mediumSizePrice = Integer.parseInt($$("button").findBy(text(addToCartButtonText)).$("[class*='currency_nowrap'] span").shouldNotBe(animated).getText());

        Assertions.assertTrue(largeSizePrice > mediumSizePrice, "Цена на большую пиццу \"" + largeSizePrice + "\" должна быть больше, чем цена на среднюю \"" + mediumSizePrice + "\"");

        $$("li[role='menuitem']")
                .findBy(text(toppingName))
                .scrollIntoView((instant().block(center)))
                .shouldBe(visible).click();

        int ingredientPrice = Integer.parseInt($$("li[role='menuitem']").findBy(text(toppingName)).$("span [class*='currency_nowrap']").getOwnText().trim());

        int finalPrice = Integer.parseInt($$("button").findBy(text(addToCartButtonText)).$("[class*='currency_nowrap'] span").shouldNotBe(animated).getText());

        Assertions.assertEquals(finalPrice, mediumSizePrice + ingredientPrice, "Итоговая сумма должна увеличиться на цену топпинга");

        $$("[class*='editor-buttons_editor']")
                .findBy(text(addToCartButtonText))
                .shouldBe(visible).click();

        $$("[class*='buy-button_small']")
                .findBy(text(cartItemCountText))
                .shouldBe(visible);

        actions().sendKeys(Keys.ESCAPE).perform();

        $("[class*='entry-card_info']").shouldHave(text(pizzaName + " " + mediumSizeText), text(toppingName), text(String.valueOf(finalPrice)));
    }
}
