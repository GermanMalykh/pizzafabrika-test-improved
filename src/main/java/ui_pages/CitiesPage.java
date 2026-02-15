package ui_pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ui_pages.Data.*;

public class CitiesPage extends BasePage<CitiesPage> {

    @Override
    public String url() {
        return "";
    }

    private static final SelenideElement CURRENT_LOCATION_BUTTON = $("button [class*='current-user-location']"),
            DELIVERY_ADDRESS_BUTTON = $("[data-testid='delivery-logic_unknown-card']"),
            DElIVERY_CITY_NAME = $("[class*='polygon-info_title']"),
            BACK_BUTTON = $("[class*='back-button']"),
            TAKEAWAY_CITY_NAME = $("[class*='selfdelivery-districts-modal'] h3"),
            CART_ITEMS_INFO = $("[class*='entry-card_info']");
    private static final ElementsCollection CITY_BUTTON = $$("[class*='list-item_content']"),
            DELIVERY_SWITCHER = $$("[class*='switcher_tab'] span"),
            TAKEAWAY_ADDRESS_BUTTON = $$("[class*='list_container'] li [class*='radioButton_label-block']");

    @Step("Выбираем город {cityName}")
    public CitiesPage selectCity(String cityName) {
        CITY_BUTTON
                .findBy(text(cityName))
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Проверяем выбор города {cityName}")
    public CitiesPage checkCity(String cityName) {
        CURRENT_LOCATION_BUTTON.shouldHave(text(cityName));
        return this;
    }

    @Step("Выбираем вариант доставки курьером")
    public CitiesPage chooseDelivery() {
        DELIVERY_SWITCHER
                .findBy(text(DELIVERY_TAB_TEXT.getValue()))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Нажимаем на кнопку для указания адреса доставки")
    public CitiesPage selectDeliveryAddress() {
        DELIVERY_ADDRESS_BUTTON.click();
        return this;
    }

    @Step("Проверяем город доставки {cityName}")
    public CitiesPage checkDeliveryCity(String cityName) {
        DElIVERY_CITY_NAME
                .shouldHave(text(cityName));
        return this;
    }

    @Step("Закрываем окно выбора адреса доставки")
    public  CitiesPage closeDeliveryWindow() {
        BACK_BUTTON.click();
        return this;
    }

    @Step("Выбираем вариант самовывоза")
    public CitiesPage chooseTakeaway() {
        DELIVERY_SWITCHER
                .findBy(text(TAKEAWAY_TAB_TEXT.getValue()))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Проверяем город самовывоза {cityName}")
    public CitiesPage checkTakeawayCity(String cityName) {
        TAKEAWAY_CITY_NAME.shouldHave(text(cityName));
        return this;
    }

    @Step("Выбираем адрес ресторана для самовывоза")
    public CitiesPage selectTakeawayAddress() {
        TAKEAWAY_ADDRESS_BUTTON.findBy(text(TAKEAWAY_ADDRESS.getValue())).shouldBe(visible).click();
        return this;
    }

    @Step("Нажимаем на кнопку выбора города")
    public CitiesPage chooseLocation() {
        CURRENT_LOCATION_BUTTON.click();
        return this;
    }

    @Step("Выбираем город {altCityName}")
    public CitiesPage selectAlternativeCity(String altCityName) {
        CITY_BUTTON
                .findBy(text(altCityName))
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Проверяем, что корзина не очистилась")
    public CitiesPage checkCartItems() {
        CART_ITEMS_INFO.shouldHave(text(PIZZA_NAME.getValue() + " " + LARGE_SIZE_TEXT.getValue()));
        return this;
    }
}