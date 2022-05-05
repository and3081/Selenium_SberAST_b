package Pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page класс страницы Яндекс Маркет
 */
public class PageYandexMarketMain extends BasePage {
    /**
     * xPath кнопки <Каталог>
     */
    public static final String XPATH_BUTTON_CATALOG = "//button[@id='catalogPopupButton']";
    /**
     * xPath пункта <Компьютеры> в меню каталога
     */
    public static final String XPATH_MENU_COMPUTERS = "//li/a[.//text()='Компьютеры']";
    /**
     * xPath заголовка <Компьютеры> после открытия пункта Компьютеры в меню каталога
     */
    public static final String XPATH_HEAD_COMPUTERS = "//div/a[.//text()='Компьютеры']";
    /**
     * xPath пункта <Ноутбуки> в меню <Компьютеры>
     */
    public static final String XPATH_MENU_NOTEBOOKS = "//li//a[./text()='Ноутбуки']";
    /**
     * xPath меню <Хлебные крошки>
     */
    public static final String XPATH_CRUMBS = "//a/span[./@itemprop]";

    public PageYandexMarketMain checkYandexMarketTitle() {
        $x(XPATH_TITLE)
                .shouldHave(attribute("textContent", TITLE_YANDEX_MARKET));
        return this;
    }

    public PageYandexMarketMain clickCatalogButton() {
        $x(XPATH_BUTTON_CATALOG).shouldBe(visible, enabled).click();
        return this;
    }
}
