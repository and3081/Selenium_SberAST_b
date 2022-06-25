package ru.vasyukov.hooks;

import custom.listeners.Listeners;
import custom.properties.TestData;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

/**
 * Класс хуков для настройки web драйвера по проперти browser.properties:
 *  - локального
 *  - удаленного (Selenide)
 *  - ремоут (Selenoid)
 */
public class WebHooks {
    /**
     * объект WebDriver
     */
    protected WebDriver driver;
    /**
     * Объект Listeners в зависимости от настройки в проперти или null
     */
    private final WebDriverListener listener = Listeners.getListener();

    /**
     * Открытие браузера перед каждым тест-кейсом
     */
    @BeforeEach
    @Step("step  . Открытие браузера")
    protected void openBrowsers() {
        String typeBrowser = TestData.browser.typeBrowser();
        if (typeBrowser !=null && typeBrowser.equals("edge")) {
            if (listener==null) driver = initEdge();
            else driver = new EventFiringDecorator(listener).decorate(initEdge());
        } else {
            if (listener==null) driver = initChrome();
            else driver = new EventFiringDecorator(listener).decorate(initChrome());
        }
    }

    /**
     * Закрытие браузера после каждого тест-кейса
     */
    @AfterEach
    @Step("step end. Закрытие браузера")
    protected void closeBrowsers() {
        if (driver != null && TestData.browser.dontCloseBrowser() ==null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Опции и открытие драйвера Chrome и его дефолт-настройки
     * Путь к chromedriver.exe в сист.переменной CHROME_DRIVER
     */
    private WebDriver initChrome() {
        if (TestData.browser.remoteUrl() != null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "chrome");
            capabilities.setCapability("browserVersion", "101.0");
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true));
            try {
                driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
                setDriverDefaultSettings();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            System.setProperty("webdriver.chrome.driver",
                    System.getenv(TestData.browser.webdriverChromeLocalPath())); //, "drivers/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            if (TestData.browser.headlessMode() != null)
                options.addArguments("--headless");
            driver = new ChromeDriver(options);
        }
        setDriverDefaultSettings();
        return driver;
    }

    /**
     * Опции и открытие драйвера Edge и его дефолт-настройки
     * Путь к msedgedriver.exe в сист.переменной EDGE_DRIVER
     */
    private WebDriver initEdge() {
        System.setProperty("webdriver.edge.driver",
                System.getenv(TestData.browser.webdriverEdgeLocalPath())); //, "drivers/chromedriver.exe");
        EdgeOptions options = new EdgeOptions();
        if (TestData.browser.headlessMode() !=null)
            options.addArguments("--headless");
        driver = new EdgeDriver(options);
        setDriverDefaultSettings();
        return driver;
    }

    /**
     * Дефолт-настройки браузера: max окно, неявные ожидания, удаление куки
     */
    private void setDriverDefaultSettings() {
        driver.manage().window().maximize();
        long timeoutMs = Long.parseLong(TestData.browser.defaultTimeoutImplicitMs());
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(timeoutMs));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(timeoutMs));
        driver.manage().timeouts().scriptTimeout(Duration.ofMillis(timeoutMs));
        driver.manage().deleteAllCookies();
    }
}
