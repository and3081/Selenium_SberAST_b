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
     * @return  стрим аргументов: список (Раздел и Подраздел каталога),
     *                            параметризация производителей
     *                            количество Просмотра для старой версии (48 для ускорения тестирования)
     */
    protected static Stream<Arguments> providerYandexMarket() {
        List<String> listChapters = List.of("Электроника", "Смартфоны");
        String countForOld = "48";  //"12";
        return Stream.of(arguments(listChapters, "Apple", countForOld));
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
//                         arguments(listChapters, "ZTE", countForOld));
    }
}
