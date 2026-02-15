package ui_pages;

import com.codeborne.selenide.*;

public abstract class BasePage<T extends BasePage> {

    public abstract String url();
    public T open() {
        String pageUrl = url();
        return Selenide.open(pageUrl, (Class<T>) this.getClass());
    }

    public <T extends BasePage> T getPage(Class<T> pageClass) {
        return Selenide.page(pageClass);
    }
}
