package pages;

import custom.properties.TestData;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Класс родительского PO (общие xpath и методы, методы создания PO страниц)
 */
public class BasePage {
    /**
     * объект WebDriver
     */
    protected static WebDriver driver;
    /**
     * Значение явного ожидания ms из проперти
     */
    protected static long timeoutExplicitMs = Long.parseLong(TestData.browser.defaultTimeoutExplicitMs());
    /**
     * Объект явных ожиданий
     */
    protected static WebDriverWait wait;
    /**
     * Объект Actions
     */
    protected static Actions actions;

    /**
     * Инициализация объектов
     * @param driver веб-драйвер
     */
    private static void init(WebDriver driver) {
        BasePage.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofMillis(timeoutExplicitMs));
        actions = new Actions(driver);
    }

    /**
     * Шаг Инициализация и Открытие стартовой страницы Сбер-АСТ
     * static
     * @param step   номер шага для аллюра
     * @param driver веб-драйвер
     * @return PO PageSberAstMain
     */
    @Step("step {step}. Открыть стартовую страницу Сбер-АСТ")  // step 1
    public static PageSberAstMain openFirstPageSberAst(int step, WebDriver driver) {
        init(driver);
        driver.get(TestData.application.baseUrlSberAst());
        return new PageSberAstMain();
    }

    /**
     * Шаг Проверить фрагмент title страницы
     * @param step  номер шага для аллюра
     * @param title фрагмент title
     */
    @Step("step {step}. Проверить фрагмент title страницы '{title}'")  // step 2
    public void checkTitleFragment(int step, String title) {
        myAssert(()->wait.until(ExpectedConditions.titleContains(title)),
                "Ожидание фрагмента title исчерпано: " + title);
    }

    /**
     * Ожидание поиска и видимости input-элемента, клик/очистка/ввод текста в него/Enter
     * @param xpath    xpath input-элемента
     * @param text     тест для ввода
     * @param message  доп.сообщение для ассерта
     * @return WebElement
     */
    public WebElement waitVisibleInputEnter(String xpath, String text, String message) {
        WebElement el = myAssert(() -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))),
                "Ожидание поиска и видимости элемента исчерпано: " + message);
        inputTextEnter(el, text);
        return el;
    }

    /**
     * Работа с полем ввода: клик для фокуса, очистка, ввод текста, Enter
     * @param el   элемент ввода
     * @param text текст для ввода
     */
    public void inputTextEnter(WebElement el, String text) {
        el.click();
        el.clear();
        el.sendKeys(text+Keys.ENTER);
    }

    /**
     * Ожидание существования списка элементов
     * @param xpath    xpath элементов
     * @param message  доп.сообщение для ассерта
     * @return список WebElement
     */
    public List<WebElement> waitPresenceList(String xpath, String message) {
        return myAssert(()->wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath))),
                "Ожидание существования списка элементов исчерпано: " + message);
    }

    /**
     * Ожидание поиска и видимости элемента
     * @param xpath    xpath элемента
     * @param message  доп.сообщение для ассерта
     * @return WebElement
     */
    public WebElement waitVisibleXpath(String xpath, String message) {
        return myAssert(()->wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))),
                "Ожидание поиска и видимости элемента исчерпано: " + message);
    }

    /**
     * Ожидание поиска, видимости и кликабельности элемента
     * @param xpath    xpath элемента
     * @param message  доп.сообщение для ассерта
     * @return WebElement
     */
    public WebElement waitVisibleClickableXpath(String xpath, String message) {
        waitVisibleXpath(xpath, message);
        return myAssert(()->wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))),
                "Ожидание элемента clickable исчерпано: " + message);
    }

    /**
     * Ожидание поиска элемента с атрибутом с фрагментом значения
     * @param xpath    xpath элемента
     * @param attr     атрибут
     * @param value    фрагмент значения
     * @param message  доп.сообщение для ассерта
     * @return WebElement
     */
    public boolean waitXpathAttributeContain(String xpath, String attr, String value, String message) {
        return myAssert(()->wait.until(ExpectedConditions.attributeContains(By.xpath(xpath),
                        attr, value)),
                "Ожидание поиска элемента с аттрибутом: " + attr +"/"+ value +"/"+ message);
    }

    /**
     * Обертка для явных ожиданий
     * Все ожидания wait() обернуты в ассерт assertTimeoutPreemptively с таймаутом из проперти для:
     * - устраняется баг по цеплянию неявного ожидания
     * @param supplier  оборачиваемый executable wait (лямбда supplier)
     *                  usage: ()->wait.until(...)
     * @param message   доп.сообщение для ассерта
     * @param <T>       параметризованный тип (определяется по supplier)
     * @return          при успехе транслирует возврат от условия wait()
     */
    private <T> T myAssert(ThrowingSupplier<T> supplier, String message) {
        return Assertions.assertTimeoutPreemptively(Duration.ofMillis(timeoutExplicitMs), supplier, message);
    }

    /**
     * Ожидание и выполнение реального клика, при ElementClickInterceptedException (перекрытие элемента)
     * отправляется ESC в фокус для попытки снятия попапа
     * @param el  элемент для клика
     * @param xpath  для попытки заново получить элемент / null
     * @return true- клик сделан
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean waitRealClick(WebElement el, String xpath) {
        boolean[] isClick = new boolean[]{false};

        myAssert(() ->new WebDriverWait(driver, Duration.ofMillis(timeoutExplicitMs))
                        .pollingEvery(Duration.ofMillis(200))
                        .ignoreAll(List.of(TimeoutException.class))
                        .until((ExpectedCondition<Boolean>) driver -> {
                            try {
                                el.click();
                            } catch (ElementClickInterceptedException e) {
                                actions.sendKeys(Keys.ESCAPE).perform();  // попытка снять попап
                                return false;
                            } catch (Exception e) {
                                if (xpath != null) {
                                    assert driver != null;
                                    driver.findElement(By.xpath(xpath)).click();  // попытка заново получить элемент
                                }
                                return false;
                            }
                            isClick[0] = true;
                            return true; }),
                "Ожидание клика на элемент исчерпано (возможно элемент чем-то закрыт):\n" + xpath);
        return isClick[0];
    }

    /**
     * Ожидание и выполнение реального send
     * @param el     элемент для send
     * @param xpath  для попытки заново получить элемент / null
     * @param text   текст для send
     * @return true- send сделан
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean waitRealSend(WebElement el, String xpath, String text) {
        boolean[] isSend = new boolean[]{false};

        myAssert(() -> new WebDriverWait(driver, Duration.ofMillis(timeoutExplicitMs))
                        .pollingEvery(Duration.ofMillis(200))
                        .ignoreAll(List.of(TimeoutException.class))
                        .until((ExpectedCondition<Boolean>) driver -> {
                            try {
                                el.sendKeys(text);
                            } catch (Exception e) {
                                if (xpath != null) {
                                    assert driver != null;
                                    driver.findElement(By.xpath(xpath)).sendKeys(text);  // попытка заново получить элемент
                                }
                                return false;
                            }
                            isSend[0] = true;
                            return true; }),
                "Ожидание send '"+ text +"' в элемент исчерпано:\n" + xpath);
        return isSend[0];
    }
}
