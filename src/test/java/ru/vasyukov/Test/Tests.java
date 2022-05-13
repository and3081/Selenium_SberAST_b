package ru.vasyukov.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.vasyukov.Hooks.WebHooks;

import static Pages.BasePage.openFirstPageSberAst;

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
     * @param search
     */
    @DisplayName("Тестирование выборки в Сбер-АСТ")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @MethodSource("ru.vasyukov.Test.DataProvider#providerSberAst")
    public void testSberAstChoice(String search) {
        openFirstPageSberAst(1)
                .checkSberAstTitle(2)
                .inputSearchField(3, search)
                .collectAllPageResults(4, 600000.0, "RUB", "44-ФЗ", 120, 10);
    }
}
