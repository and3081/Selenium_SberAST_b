package Pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class PageYandexMarketChoice extends BasePage {
    private int versionPage = 1;  // old version
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
    /**
     * xPath элемента для анализа появления и пропадания серого экрана (версия 1)
     */
    public String XPATH_CHOICE_PROGRESS1 = "//div[@aria-label='Результаты поиска']/div";
    /**
     * xPath элемента для анализа появления и пропадания серого экрана (версия 2)
     */
    public String XPATH_CHOICE_PROGRESS2 = "//div[@aria-label='Результаты поиска']/parent::div/div";
    /**
     * xPath кнопки <Показывать по> (версия 1)
     */
    public static final String XPATH_COUNT_ITEMS1 = "//button[.//span//text()[contains(.,'Показывать по')]]";
    /**
     * xPath опций кнопки <Показывать по> (версия 1)
     */
    public static final String XPATH_COUNT_ITEMS_OPTIONS1 = XPATH_COUNT_ITEMS1 + "/following-sibling::div//button";
    /**
     * xPath title списка показанных товаров
     */
    public static final String XPATH_SEARCHED_ARTICLES_TEXT =
            "//div[@aria-label='Результаты поиска']//article//a[@title]//span[text()]";
    public String XPATH_PAGINATION_BUTTONS_1 = "//a[@aria-label[contains(.,'траница')]]";
    public String XPATH_PAGINATION_BUTTONS_2 = "//div[@data-auto[contains(.,'pagination')]]";

    @Step("step {step}. ")  // step
    public PageYandexMarketChoice checkNameInCrumbs(String step, String name) {
        $$x(XPATH_CRUMBS).shouldBe(sizeGreaterThanOrEqual(3)).get(2)
                .should(be(visible), have(exactText(name)));
        checkVersionPage();
        return this;
    }

    @Step("step {step}. ")  // step
    public PageYandexMarketChoice clickAllFactoriesButton(String step) {
        waitRealClick($x((versionPage==1) ? XPATH_ALL_FACTORIES_BUTTON_1 : XPATH_ALL_FACTORIES_BUTTON_2)
                .shouldBe(visible, enabled));
        return this;
    }

    @Step("step {step}. ")  // step
    public PageYandexMarketChoice inputFactorySearch(String step, String nameFactory) {
        $x((versionPage==1) ? XPATH_FACTORIES_SEARCH_1 : XPATH_FACTORIES_SEARCH_2)
                .shouldBe(visible, enabled).setValue(nameFactory).pressEnter();
        return this;
    }

    @Step("step {step}. ")  // step
    public PageYandexMarketChoice clickFactoryItemAndWait(String step, String nameFactory) {
        waitRealClick($x(XPATH_FACTORIES_ITEM)
                .should(be(visible), be(enabled), have(exactText(nameFactory))));
        waitEndChoice();
        return this;
    }

    @Step("step {step}. ")  // step
    public PageYandexMarketChoice selectChoiceCountViewAndWaitForOld(String step, String count) {
        if (versionPage==1 && $$x(XPATH_COUNT_ITEMS1).size()>0) {  // м.не быть кнопки if все на 1 экране
            waitRealClick($x(XPATH_COUNT_ITEMS1).shouldBe(visible, enabled));
            waitRealClick($$x(XPATH_COUNT_ITEMS_OPTIONS1).shouldBe(sizeGreaterThan(0))
                    .findBy(text(count)).shouldBe(visible, enabled));
            waitEndChoice();
        }
        return this;
    }

    @Step("step {step}. ")  // step
    public PageYandexMarketChoice checkAllPagesArticlesName(String step, String factory) {
        int i = 100;  // предохранитель
        do { checkSearchedArticlesName(factory);
        } while ((--i >0) && isClickButtonForwardAndWait());
        return this;
    }

    public PageYandexMarketChoice checkSearchedArticlesName(String factory) {
            $$x(XPATH_SEARCHED_ARTICLES_TEXT).shouldBe(sizeGreaterThan(0))
                    .excludeWith(text(factory)).shouldBe(empty);
        return this;
    }

    public boolean isClickButtonForwardAndWait() {
        ElementsCollection listFiltered;
        if (versionPage == 1) {
            listFiltered = $$x(XPATH_PAGINATION_BUTTONS_1)  //.shouldBe(sizeGreaterThan(0))
                    .filterBy(attribute("aria-label", "Следующая страница"));
        } else {
            listFiltered = $$x(XPATH_PAGINATION_BUTTONS_2)  //.shouldBe(sizeGreaterThan(0))  м.не быть if 1 экран
                    .filterBy(attribute("data-auto", "pagination-next"));
        }
        if (listFiltered.size()>0 && waitRealClick(listFiltered.get(0)
                .scrollIntoView(false).shouldBe(visible, enabled))) {  // scroll тут на всяк, иначе иногда не попадает клик
            waitEndChoice();
            return true;
        }
        return false;
    }

    /**
     * Шаг Ожидание завершения выборки
     */
    public PageYandexMarketChoice waitEndChoice() {
        if (versionPage == 1) {
            // пример старая версия изменение выборки:
            //   до:         у div aria-label='Результаты поиска' - 1 дочерний div
            //   клик в выборке
            //   серое окно: появляется доп.дочерний временный div (или несколько)
            //   результат:  доп.временный div убирается, снова 1 дочерний div
            $$x(XPATH_CHOICE_PROGRESS1).shouldBe(sizeGreaterThan(1)).shouldBe(size(1));
        } else {
            // пример новая версия изменение выборки:
            // аналогично, но доп. div появляется сестринский, а не дочерний
            $$x(XPATH_CHOICE_PROGRESS2).shouldBe(sizeGreaterThan(1)).shouldBe(size(1));
        }
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
