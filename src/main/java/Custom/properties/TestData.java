package Custom.properties;

import org.aeonbits.owner.ConfigFactory;

/**
 * Класс экзекутор для работы с проперти
 * usage:  TestData.props.имяМетодаКлюча()
 */
public class TestData {
    /**
     * static метод для работы с проперти из файла props.properties
     */
    public static Props props = ConfigFactory.create(Props.class);
}
