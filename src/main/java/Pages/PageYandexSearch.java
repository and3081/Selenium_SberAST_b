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

    public PageYandexSearch checkYandexTitle() {
        $x(XPATH_TITLE).shouldHave(attribute("textContent", TITLE_YANDEX));
        return this;
    }

    public PageYandexSearch clickYandexMarketAndSwitch() {
        $x(XPATH_ICON_YANDEX_MARKET).shouldBe(visible, enabled).click();
        switchTo().window(TITLE_YANDEX_MARKET);
        return this;
    }

    public PageYandexMarketMain nextPageYandexMarketMain() {
        return page(PageYandexMarketMain.class);
    }
}
