package ru.vasyukov;

import Custom.listeners.Listeners;
import Custom.properties.TestData;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import static com.codeborne.selenide.WebDriverRunner.*;

public class BaseTests {
    /**
     * Объект Listeners в зависимости от настройки в проперти или null
     */
    private final WebDriverListener listener = Listeners.getListener();

    @BeforeEach
    public void options() {
        Configuration.timeout=Long.parseLong(TestData.props.defaultTimeoutImplicitMs());
        if (listener!=null) addListener(listener);
        if (TestData.props.headlessMode() !=null) Configuration.headless = true;
        if (TestData.props.dontCloseBrowser() !=null) Configuration.holdBrowserOpen = true;
        if (TestData.props.typeBrowser() !=null)
            if (TestData.props.typeBrowser().equals("edge")) {
                System.setProperty("webdriver.edge.driver",
                        System.getenv(TestData.props.webdriverEdgeLocalPath()));
                WebDriver driver = new EdgeDriver();
                setWebDriver(driver);
            } else Configuration.browser=TestData.props.typeBrowser();
        else Configuration.browser="chrome";

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized");  // устарел, use  getWebDriver().manage().window().maximize();
//        DesiredCapabilities capabilities = new DesiredCapabilities();  // old
//        MutableCapabilities capabilities = new MutableCapabilities();  // new
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        Configuration.browserCapabilities = capabilities;

//        System.setProperty("webdriver.chrome.driver", System.getenv("drivers/"));
//        WebDriver driver;
//        driver = new ChromeDriver(options);
//        setWebDriver(driver);
    }

    @AfterEach
    public void close() {
        closeWindow();  // для повтора теста по производителям
    }
}
