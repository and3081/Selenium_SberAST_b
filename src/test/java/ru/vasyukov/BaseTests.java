package ru.vasyukov;

import Custom.properties.TestData;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;

public class BaseTests {

    @BeforeEach
    public void options() {
        Configuration.timeout=Long.parseLong(TestData.props.defaultTimeoutImplicit());
        if (TestData.props.typeBrowser() !=null)
            Configuration.browser=TestData.props.typeBrowser();
        else
            Configuration.browser="chrome";
        Configuration.startMaximized = true;
        if (TestData.props.headlessMode() !=null)
            Configuration.headless = true;
        if (TestData.props.dontCloseBrowser() !=null)
            Configuration.holdBrowserOpen = true;

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
