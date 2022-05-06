package ru.vasyukov;

import Custom.properties.TestData;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class BaseTests {

    @BeforeEach
    public void options() {
        Configuration.timeout=Long.parseLong(TestData.props.defaultTimeoutImplicitMs());
        Configuration.startMaximized = true;
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
