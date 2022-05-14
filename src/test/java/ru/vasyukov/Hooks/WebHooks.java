package ru.vasyukov.Hooks;

import Custom.listeners.Listeners;
import Custom.properties.TestData;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.time.Duration;

/**
 * Родительский класс для тестов:
 *   настройка опций браузера и листенера первый раз,
 *   методы BeforeEach и AfterEach
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
    @Step("step 1. Открытие браузера")
    protected void openBrowsers() {
        String typeBrowser = TestData.props.typeBrowser();
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
        if (driver != null && TestData.props.dontCloseBrowser() ==null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Опции и открытие драйвера Chrome и его дефолт-настройки
     * Путь к chromedriver.exe в сист.переменной CHROME_DRIVER
     */
    private WebDriver initChrome() {
        System.setProperty("webdriver.chrome.driver",
                System.getenv(TestData.props.webdriverChromeLocalPath())); //, "drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        if (TestData.props.headlessMode() !=null)
            options.addArguments("--headless");
        driver = new ChromeDriver(options);
        setDriverDefaultSettings();
        return driver;
    }

    /**
     * Опции и открытие драйвера Edge и его дефолт-настройки
     * Путь к msedgedriver.exe в сист.переменной EDGE_DRIVER
     */
    private WebDriver initEdge() {
        System.setProperty("webdriver.edge.driver",
                System.getenv(TestData.props.webdriverEdgeLocalPath())); //, "drivers/chromedriver.exe");
        EdgeOptions options = new EdgeOptions();
        if (TestData.props.headlessMode() !=null)
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
        long timeout = Long.parseLong(TestData.props.defaultTimeoutImplicitMs());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeout));
        driver.manage().deleteAllCookies();
    }
}
