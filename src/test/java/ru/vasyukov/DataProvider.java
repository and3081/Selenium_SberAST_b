package ru.vasyukov;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Класс провайдера данных для тестов
 */
public class DataProvider {
    /**
     * Метод-провайдер для тест-кейса testYandexMarketChoice()
     * @return  стрим аргументов: список (цена от, цена до),
     *                            список производителей
     *                            список (количество Просмотра для v.1 (на выбор)
     *                                    количество Просмотра для v.2 (удвоенное при Просмотреть еще))
     */
    protected static Stream<Arguments> providerYandexMarket() {
        List<String> listChapters = List.of("Электроника", "Смартфоны");
        String countForOld = "48";  //"12";
        return Stream.of(arguments(listChapters, "Apple", countForOld),
//                         arguments(listChapters, "Google", countForOld),
//                         arguments(listChapters, "HONOR", countForOld),
//                         arguments(listChapters, "HUAWEI", countForOld),
//                         arguments(listChapters, "Nokia", countForOld),
//                         arguments(listChapters, "OnePlus", countForOld),
//                         arguments(listChapters, "OPPO", countForOld),
//                         arguments(listChapters, "realme", countForOld),
//                         arguments(listChapters, "Samsung", countForOld),
//                         arguments(listChapters, "vivo", countForOld),
//                         arguments(listChapters, "Xiaomi", countForOld),
                         arguments(listChapters, "ZTE", countForOld));
    }
}
