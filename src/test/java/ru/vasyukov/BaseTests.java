package ru.vasyukov;

import Custom.listeners.Listeners;
import Custom.properties.TestData;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import static com.codeborne.selenide.WebDriverRunner.addListener;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class BaseTests {
    /**
     * Объект Listeners в зависимости от настройки в проперти или null
     */
    private final WebDriverListener listener = Listeners.getListener();

    @BeforeEach
    public void options() {
        Configuration.timeout=Long.parseLong(TestData.props.defaultTimeoutImplicitMs());

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized");
//
//        MutableCapabilities capabilities = new MutableCapabilities();
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        Configuration.browserCapabilities = capabilities;
        //addListener(listener);
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
//        options.addArguments("start-maximized");
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        Configuration.browserCapabilities = capabilities;

//        System.setProperty("webdriver.chrome.driver", System.getenv("drivers/"));
//        WebDriver driver;
//        driver = new ChromeDriver(options);
//        setWebDriver(driver);
    }
}
