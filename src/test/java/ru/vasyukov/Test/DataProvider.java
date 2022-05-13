package ru.vasyukov.Test;

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
     */
    protected static Stream<Arguments> providerSberAst() {
        return Stream.of(arguments("Страхование"));
    }
}
