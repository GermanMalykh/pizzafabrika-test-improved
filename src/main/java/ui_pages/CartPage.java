package ui_pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class CartPage extends BasePage<CartPage> {
    @Override
    public String url() {
        return "";
    }

    private SelenideElement cartInfo = Selenide.$("[class*='entry-card_info']");

    @Step("Проверяем наличие товаров в корзине")
    public CartPage checkCart(String pizzaName, String mediumSizeText, String toppingName, int finalPrice) {
        cartInfo.shouldHave(Condition.text(pizzaName + " " + mediumSizeText), Condition.text(toppingName), Condition.text(String.valueOf(finalPrice)));
        return this;
    }
}
