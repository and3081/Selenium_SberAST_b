package ru.vasyukov;

import Custom.properties.TestData;
import Pages.PageYandexSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static Pages.BasePage.openFirstPageYandexSearch;
import static com.codeborne.selenide.Selenide.open;

/**
 * Класс тест-кейсов с настраиваемыми листенерами в проперти:
 *       - какие методы скринить: драйвер, элементы, все варианты или отключить
 *       - скрининг элементов:    всё окно, только элемент или оба скрина сразу
 *
 *       В аллюре скрины подписаны- перед и после какого метода, аргументы и возврат метода
 *
 *       - выбор браузера для прогона тестов:  Chrome, Edge или сразу оба
 *
 * @author Васюков А.Ю.  GitHub  https://github.com/and3081/AT-Framework-YMarket
 * @version 1.0
 */
public class Tests extends BaseTests {
    /**
     * Тест-кейс выборки и поиска в Яндекс-маркет (версии v.1 и v.2)
     * параметры поставляются провайдером данных providerYandexMarket()
     * @param factories  список названий Производителей
     * @param countForOld     количество Просмотра для v.1 (на выбор) и v.2 (удвоенное при Просмотреть еще)
     */
    @DisplayName("Тестирование выборки в Яндекс-маркет")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @MethodSource("ru.vasyukov.DataProvider#providerYandexMarket")
    public void testYandexMarketChoice(List<String> itemsNameMenu, List<String> factories, String countForOld) {
        openFirstPageYandexSearch()
                .checkYandexTitle()
                .clickYandexMarketAndSwitch().nextPageYandexMarketMain()
                .checkYandexMarketTitle()
                .clickCatalogButton()
                .clickItemCatalog(itemsNameMenu.get(0))
                .checkHeadChapterCatalog(itemsNameMenu.get(0))
                .clickItemCatalog(itemsNameMenu.get(1)).nextPageYandexMarketChoice()
                .checkNameInCrumbs(itemsNameMenu.get(1))
                .clickAllFactoriesButton()
                .inputFactorySearch(factories.get(0))
                .clickFactoryItemAndWait(factories.get(0))
                .selectChoiceCountViewAndWaitV1(countForOld)
                .checkAllPagesArticlesName(factories.get(0));
    }
}
