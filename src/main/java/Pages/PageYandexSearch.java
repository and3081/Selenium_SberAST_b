package Pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page класс поисковой страницы https://yandex.ru/
 */
public class PageYandexSearch extends BasePage {
    /**
     * xPath клик-иконки Яндекс Маркета
     */
    public String XPATH_ICON_YANDEX_MARKET = "//a[@data-id='market']";

    public PageYandexSearch checkYandexTitle(String step) {
        checkTitle(TITLE_YANDEX);
        return this;
    }

    public PageYandexSearch clickYandexMarketAndSwitch(String step) {
        waitRealClick($x(XPATH_ICON_YANDEX_MARKET).shouldBe(visible, enabled));
        switchTo().window(TITLE_YANDEX_MARKET);
        return this;
    }
}
