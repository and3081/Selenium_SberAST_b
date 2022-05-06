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
        return Stream.of(arguments(List.of("Электроника", "Смартфоны"),
                List.of("Apple", "Google", "HONOR", "HUAWEI", "Nokia", "OnePlus", "OPPO",
                        "realme", "Samsung", "vivo", "Xiaomi", "ZTE"),
                List.of("12", "96")));
    }
}
