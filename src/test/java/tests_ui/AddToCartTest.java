package tests_ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui_pages.CartPage;
import ui_pages.CatalogPage;

import static ui_pages.Data.*;

public class AddToCartTest extends BaseUITest {
    private final CatalogPage catalog = new CatalogPage();

    @Test
    @DisplayName("Добавление товара в корзину")
    void addPizzaToCartTest() {
        catalog.open()
                .goToPizzaSection()
                .scrollAndSelectItem(PIZZA_NAME.getValue())
                .selectLargePizza(LARGE_SIZE_TEXT.getValue());

        int largeSizePrice = catalog.getPizzaPrice();

        catalog.selectMediumPizza(MEDIUM_SIZE_TEXT.getValue());

        int mediumSizePrice = catalog.getPizzaPrice();

        Assertions.assertTrue(largeSizePrice > mediumSizePrice, "Цена на большую пиццу \"" + largeSizePrice + "\" должна быть больше, чем цена на среднюю \"" + mediumSizePrice + "\"");

        catalog.selectTopping(TOPPING_NAME.getValue());

        int ingredientPrice = catalog.getToppingPrice();

        int finalPrice = catalog.getPizzaPrice();

        Assertions.assertEquals(finalPrice, mediumSizePrice + ingredientPrice, "Итоговая сумма должна увеличиться на цену топпинга");

        catalog.addToCart(ADD_TO_CART_BUTTON_TEXT.getValue())
                .countCartItem(CART_ITEM_COUNT_TEXT.getValue())
                .pressEscape()
                .getPage(CartPage.class)
                .checkCart(PIZZA_NAME.getValue(), MEDIUM_SIZE_TEXT.getValue(), TOPPING_NAME.getValue(), finalPrice);
    }
}
