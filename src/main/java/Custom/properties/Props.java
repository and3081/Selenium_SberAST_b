package Custom.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для работы с проперти из файла props.properties и системными проперти
 * usage:  TestData.props.имяМетодаКлюча()
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:props.properties"
})
public interface Props extends Config {
    @Key("base.url.yandex")
    String baseUrlYandex();

    @Key("webdriver.chrome.local.path")
    String webdriverChromeLocalPath();

    @Key("webdriver.edge.local.path")
    String webdriverEdgeLocalPath();

    @Key("default.timeout.implicit")
    String defaultTimeoutImplicit();

    @Key("default.timeout.explicit")
    String defaultTimeoutExplicit();

    @Key("listener.type")
    String listenerType();

    @Key("listener.mode.elements")
    String listenerModeElements();

    @Key("type.browser")
    String typeBrowser();

    @Key("dont.close.browser")
    String dontCloseBrowser();

    @Key("headless.mode")
    String headlessMode();
}
