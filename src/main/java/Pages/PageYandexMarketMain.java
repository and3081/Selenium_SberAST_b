package Pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page класс страницы Яндекс Маркет
 */
public class PageYandexMarketMain extends BasePage {
    /**
     * xPath кнопки <Каталог>
     */
    public String XPATH_BUTTON_CATALOG = "//button[@id='catalogPopupButton']";
    /**
     * xPath пункта <Компьютеры> в меню каталога
     */
    public String XPATH_ITEM_MENU = "//li//a[.//text()='REPLACEMENT']";
    /**
     * xPath заголовка <Компьютеры> после открытия пункта Компьютеры в меню каталога
     */
    public String XPATH_HEAD_CHAPTER_CATALOG = "//div/h1[text()='REPLACEMENT']";

    @Step("step {step}. ")  // step
    public PageYandexMarketMain checkYandexMarketTitle(String step) {
        checkTitle(TITLE_YANDEX_MARKET);
        return this;
    }

    @Step("step {step}. Переходим по кнопке в каталог и наводим на раздел Компьютеры")  // step 4
    public PageYandexMarketMain clickCatalogButton(String step) {
        waitRealClick($x(XPATH_BUTTON_CATALOG).shouldBe(visible, enabled));
        return this;
    }

    @Step("step {step}. ")  // step
    public PageYandexMarketMain clickItemCatalog(String step, String itemNameMenu) {
        waitRealClick($x(XPATH_ITEM_MENU.replace("REPLACEMENT", itemNameMenu)).shouldBe(visible, enabled));
        return this;
    }

    @Step("step {step}. ")  // step
    public PageYandexMarketMain checkHeadChapterCatalog(String step, String itemNameMenu) {
        $x(XPATH_HEAD_CHAPTER_CATALOG.replace("REPLACEMENT", itemNameMenu)).shouldBe(visible);
        return this;
    }
}
