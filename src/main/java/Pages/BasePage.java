package Pages;

import Custom.properties.TestData;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BasePage {
    /**
     * Значение явного ожидания из проперти
     */
    public long timeoutExplicitMs = Long.parseLong(TestData.props.defaultTimeoutExplicitMs());
    /**
     * Привязанный объект явных ожиданий
     */
    private final WebDriverWait waitClick = new WebDriverWait(getWebDriver(), Duration.ofMillis(timeoutExplicitMs));

    public String XPATH_TITLE = "//head/title";
    /**
     * title базовой страницы
     */
    public String TITLE_YANDEX = "Яндекс";
    /**
     * title базовой страницы Яндекс Маркет
     */
    public String TITLE_YANDEX_MARKET =
            "Интернет-магазин Яндекс.Маркет — покупки с быстрой доставкой";

    @Step("step {step}. Проверить title страницы '{title}'")  // step 2/4
    public void checkTitle(String step, String title) {
        $x(XPATH_TITLE).shouldHave(attribute("textContent", title));
    }

    public static void maxWindow() { getWebDriver().manage().window().maximize(); }

    @Step("step {step}. Открыть браузер и стартовую страницу Яндекс")  // step 1
    public static PageYandexSearch openFirstPageYandexSearch(String step) {
        open(TestData.props.baseUrlYandex());
        maxWindow();
        return page(PageYandexSearch.class); }

    public PageYandexMarketMain nextPageYandexMarketMain() { return page(PageYandexMarketMain.class); }

    public PageYandexMarketChoice nextPageYandexMarketChoice() { return page(PageYandexMarketChoice.class); }

    /**
     * Ожидание и выполнение реального клика, при ElementClickInterceptedException (перекрытие элемента)
     * отправляется ESC в фокус для попытки снятия попапа
     * @param el       элемент для клика
     */
    public boolean waitRealClick(SelenideElement el) {
        boolean[] isClick = new boolean[]{false};
        waitClick.until((ExpectedCondition<Boolean>) x->{
            try { el.toWebElement().click();  // селениумным кликом !! иначе селенидный исключение блокирует
            } catch (ElementClickInterceptedException e) {
                actions().sendKeys(Keys.ESCAPE).perform();  // попытка снять попап
                return false;
            } isClick[0] = true; return true; });
        return isClick[0];
    }
}
