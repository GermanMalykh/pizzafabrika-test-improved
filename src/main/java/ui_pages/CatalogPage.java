package ui_pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;
import static ui_pages.Data.*;

public class CatalogPage extends BasePage<CatalogPage> {

    @Override
    public String url() {
        return "/vologda";
    }

    private static final String PIZZA_PRICE_TEXT = "[class*='currency_nowrap'] span";
    private static final String TOPPING_PRICE_TEXT = "span [class*='currency_nowrap']";

    private final static SelenideElement PIZZA_SECTION = $("[data-testid='pizza']");
    private final static ElementsCollection PIZZA_BUTTON = Selenide.$$("#pizza article[role='button']"),
                                            SIZE_SWITCHER = Selenide.$$("[class*='switcher_tab-label']"),
                                            BUTTON = Selenide.$$("button"),
                                            TOPPING_BUTTON = Selenide.$$("li[role='menuitem']"),
                                            ADD_TO_CART_BUTTON = Selenide.$$("[class*='editor-buttons_editor']"),
                                            CART_ITEM_COUNT_BUTTON = Selenide.$$("[class*='buy-button_small']");

    @Step("Ищем раздел меню \"Пицца\"")
    public CatalogPage goToPizzaSection() {
        PIZZA_SECTION.click();
        return this;
    }

    @Step("Прокручиваем список и выбираем нужный товар \"{pizzaName}\"")
    public CatalogPage scrollAndSelectItem(String pizzaName) {
                PIZZA_BUTTON
                .findBy(Condition.text(PIZZA_NAME.getValue()))
                .scrollIntoView((ScrollIntoViewOptions.instant().block(ScrollIntoViewOptions.Block.center)))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Кликаем на кнопку размера пиццы \"{largeSizeText}\"")
    public CatalogPage selectLargePizza(String largeSizeText) {
                SIZE_SWITCHER
                .findBy(Condition.text(largeSizeText))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Получаем цену пиццы")
    public int getPizzaPrice() {
        String pizzaPrice = BUTTON.findBy(Condition.text(ADD_TO_CART_BUTTON_TEXT.getValue())).$(PIZZA_PRICE_TEXT).shouldNotBe(Condition.animated).getText();
        return Integer.parseInt(pizzaPrice);
    }

    @Step("Кликаем на кнопку размера пиццы \"{mediumSizeText}\"")
    public CatalogPage selectMediumPizza(String mediumSizeText) {
                SIZE_SWITCHER
                .findBy(Condition.text(mediumSizeText))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Выбираем топпинг")
    public CatalogPage selectTopping(String toppingName) {
                TOPPING_BUTTON
                .findBy(Condition.text(toppingName))
                .scrollIntoView((ScrollIntoViewOptions.instant().block(ScrollIntoViewOptions.Block.center)))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Получаем цену топпинга")
    public int getToppingPrice() {
        String toppingPrice = TOPPING_BUTTON.findBy(Condition.text(TOPPING_NAME.getValue())).$(TOPPING_PRICE_TEXT).getOwnText().trim();
        return Integer.parseInt(toppingPrice);
    }

    @Step("Кликаем на кнопку \"{addToCartButtonText}\"")
    public CatalogPage addToCart(String addToCartButtonText) {
                ADD_TO_CART_BUTTON
                .findBy(Condition.text(ADD_TO_CART_BUTTON_TEXT.getValue()))
                .shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Проверяем количество выбранных товаров")
    public CatalogPage countCartItem(String cartItemCountText) {
                CART_ITEM_COUNT_BUTTON
                .findBy(Condition.text(CART_ITEM_COUNT_TEXT.getValue()))
                .shouldBe(Condition.visible);
        return this;
    }

    @Step("Закрываем окно")
    public CatalogPage pressEscape() {
        actions().sendKeys(Keys.ESCAPE).perform();
        return this;
    }
}
