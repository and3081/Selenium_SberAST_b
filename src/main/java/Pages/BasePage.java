package Pages;

import static com.codeborne.selenide.Selenide.page;

public class BasePage {
    public String XPATH_TITLE = "//head/title";
    /**
     * title базовой страницы
     */
    public String TITLE_YANDEX = "Яндекс";
    /**
     * title базовой страницы Яндекс Маркет
     */
    public String TITLE_YANDEX_MARKET =
            "Интернет-магазин Яндекс.Маркет — покупки с быстрой доставкой";

    public PageYandexMarketMain nextPageYandexMarketMain() { return page(PageYandexMarketMain.class); }

    public PageYandexMarketChoice nextPageYandexMarketChoice() { return page(PageYandexMarketChoice.class); }
}
