package Pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page класс поисковой страницы https://yandex.ru/
 */
public class PageYandexSearch {
    /**
     * title базовой страницы
     */
    public String TITLE_YANDEX = "Яндекс";
    public String XPATH_YANDEX_TITLE = "//head/title";
    /**
     * xPath клик-иконки Яндекс Маркета
     */
    public String XPATH_ICON_YANDEX_MARKET = "//a[@data-id='market']";

    public PageYandexSearch checkYandexTitle() {
        $x(XPATH_YANDEX_TITLE).shouldHave(attribute("textContent", TITLE_YANDEX));
        return this;
    }
}
