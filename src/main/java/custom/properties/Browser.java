package custom.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для работы с проперти из файла browser.properties и системными проперти
 * usage:  TestData.browser.имяМетодаКлюча()
 * Описание в файле проперти
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:browser.properties"
})
public interface Browser extends Config {
    @Key("base.url.sber.ast")
    String baseUrlSberAst();

    @Key("wdm")
    String wdm();

    @Key("webdriver.chrome.local.path")
    String webdriverChromeLocalPath();

    @Key("webdriver.chrome.getenv.path")
    String webdriverChromeGetenvPath();

    @Key("webdriver.edge.local.path")
    String webdriverEdgeLocalPath();

    @Key("webdriver.edge.getenv.path")
    String webdriverEdgeGetenvPath();

    @Key("default.timeout.implicit.ms")
    String defaultTimeoutImplicitMs();

    @Key("default.timeout.explicit.ms")
    String defaultTimeoutExplicitMs();

    @Key("type.browser")
    String typeBrowser();

    @Key("dont.close.browser")
    String dontCloseBrowser();

    @Key("headless.mode")
    String headlessMode();

    @Key("remote.url")
    String remoteUrl();
}
