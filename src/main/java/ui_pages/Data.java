package ui_pages;

import lombok.Getter;

@Getter
public enum Data {
    VOLOGDA_CITY_NAME("Вологда"),
    CHEREPOVETS_CITY_NAME("Череповец"),
    PIZZA_NAME("Мясное плато"),
    TOPPING_NAME("Шампиньоны 20г"),
    LARGE_SIZE_TEXT("Большая"),
    MEDIUM_SIZE_TEXT("Средняя"),
    TAKEAWAY_TAB_TEXT("С собой"),
    DELIVERY_TAB_TEXT("Доставка"),
    ADD_TO_CART_BUTTON_TEXT("В корзину"),
    TAKEAWAY_ADDRESS("Ресторан, Пошехонское шоссе, 10"),
    CART_ITEM_COUNT_TEXT("В корзине 1 шт.");

    private final String value;

    Data(String value) {
        this.value = value;
    }
}
