package Pages;

import com.codeborne.selenide.CollectionCondition;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class PageYandexMarketChoice extends BasePage {
    public int versionPage = 1;  // old
    /**
     * xPath меню <Хлебные крошки>
     */
    public static final String XPATH_CRUMBS = "//a/span[./@itemprop]";
    /**
     * xPath элемента для определения версии страницы выбора и поиска (версия 1 или 2)
     */
    public String XPATH_VERSION_PAGE = "//div[@aria-label='Результаты поиска']";
    /**
     * xPath заголовка блока выбора <Производителей>
     */
    public String XPATH_BASE_FACTORIES = "//fieldset[.//legend[.//text()[contains(.,'Производитель')]]]";
    /**
     * xPath кнопки <Показать всё> производителей (версия 1)
     */
    public String XPATH_ALL_FACTORIES_BUTTON_1 = XPATH_BASE_FACTORIES + "/footer/button";
    /**
     * xPath кнопки <Показать всё> производителей (версия 2)
     */
    public String XPATH_ALL_FACTORIES_BUTTON_2 = XPATH_BASE_FACTORIES + "//span[@role='button']/span";
    public String XPATH_FACTORIES_SEARCH_1 = XPATH_BASE_FACTORIES + "//input[@name='Поле поиска']";
    public String XPATH_FACTORIES_SEARCH_2 = XPATH_BASE_FACTORIES + "//div[label[text()='Найти производителя']]//input";
    public String XPATH_FACTORIES_ITEM = XPATH_BASE_FACTORIES + "//label[.//input[@type='checkbox']]//span[text()]";
    public String XPATH_VIEW_BUTTON_FORWARD_1 = "//a[@aria-label='Следующая страница']";  // text="Вперёд"
    public String XPATH_VIEW_BUTTON_FORWARD_2 = "//div[@data-auto='pagination-next']";  // text="Вперёд"

    public PageYandexMarketChoice checkNameInCrumbs(String name) {
        $$x(XPATH_CRUMBS).shouldBe(sizeGreaterThanOrEqual(3)).get(2).shouldHave(visible, exactText(name));
        return this;
    }

    public PageYandexMarketChoice clickAllFactoriesButton() {
        $x((versionPage==1) ? XPATH_ALL_FACTORIES_BUTTON_1 : XPATH_ALL_FACTORIES_BUTTON_2)
                .shouldBe(visible, enabled).click();
        return this;
    }

    public PageYandexMarketChoice inputFactorySearch(String nameFactory) {
        $x((versionPage==1) ? XPATH_FACTORIES_SEARCH_1 : XPATH_FACTORIES_SEARCH_2)
                .shouldBe(visible, enabled).setValue(nameFactory).pressEnter();
        return this;
    }

    public PageYandexMarketChoice clickFactoryItem(String nameFactory) {
        $x(XPATH_FACTORIES_ITEM)
                .should(be(visible), be(enabled), have(exactText(nameFactory))).click();
        return this;
    }

    public PageYandexMarketChoice checkVersionPage() {
        if ($x(XPATH_VERSION_PAGE)
                .should(be(exist), have(or("Запрос версии страницы",
                        attribute("data-grabber"), attribute("data-auto"))))
                .getAttribute("data-auto") !=null) {
            versionPage = 2;  // new
        }
        System.out.println("версия: " + versionPage);
        return this;
    }
}
