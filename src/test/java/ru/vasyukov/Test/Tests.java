package ru.vasyukov.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.vasyukov.Hooks.WebHooks;

import java.util.List;

import static Pages.BasePage.openFirstPageYandexSearch;

/**
 * Класс тест-кейсов
 * Настраиваемые листенеры в проперти:
 *       - какие типы методов скринить: драйвер, элементы, все варианты или отключить
 *       - какие выборочно по названиям методы скринить
 *       - какие скрины вокруг методов делать: перед, после, оба
 *       - при скрининге элементов:     всё окно, только элемент или оба скрина сразу
 *
 *       В аллюре скрины подписаны- перед и после какого метода, аргументы и возврат метода
 *
 *       - выбор браузера в проперти для прогона тестов:  Chrome, Edge
 *
 * @author Васюков А.Ю.  GitHub  https://github.com/and3081/Selenide_YMarket_b
 * @version 1.0
 * Описание тест-кейса:
 * 1. Открыть браузер и развернуть на весь экран.
 * 2. Зайти на yandex.ru.
 * 3. Перейти в яндекс маркет
 * 4. Выбрать раздел Электроника
 * 5. Выбрать раздел Смартфоны
 * 6. Задать параметр «Производитель» Apple.
 * 8. Дождаться результатов поиска.
 * 9. Установить количество показываемых элементов на страницу 48
 * 10. Убедится что в выборку попали только iPhone. Если страниц несколько – проверить все.
 * 11. Тест должен работать для любого производителя
 */
public class Tests extends WebHooks {
    /**
     * Тест-кейс выборки и поиска в Яндекс-маркет (версии v.1 и v.2)
     * параметры поставляются провайдером данных providerYandexMarket()
     * @param itemsNameMenu  список названий - Раздел и Подраздел каталога
     * @param factory        название Производителя (тест повторяется для разных производителей)
     * @param countForOld    (для старой версии) количество Просмотра (48 для ускорения тестирования)
     */
    @DisplayName("Тестирование выборки в Яндекс-маркет")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @MethodSource("ru.vasyukov.Test.DataProvider#providerYandexMarket")
    public void testYandexMarketChoice(List<String> itemsNameMenu, String factory, String countForOld) {
        openFirstPageYandexSearch("1")
                .checkYandexTitle("2")
                .clickYandexMarketAndSwitch("3").nextPageYandexMarketMain()
                .checkYandexMarketTitle("4")
                .clickCatalogButton("5")
                .clickItemCatalog("6", itemsNameMenu.get(0))
                .checkHeadChapterCatalog("7", itemsNameMenu.get(0))
                .clickItemCatalog("8", itemsNameMenu.get(1)).nextPageYandexMarketChoice()
                .checkNameInCrumbs("9", itemsNameMenu.get(1))
                .clickAllFactoriesButton("10")
                .inputFactorySearch("11", factory)
                .clickFactoryItemAndWait("12", factory)
                .selectChoiceCountViewAndWaitForOld("13", countForOld)
                .checkAllPagesArticlesName("14", factory);
    }
}
