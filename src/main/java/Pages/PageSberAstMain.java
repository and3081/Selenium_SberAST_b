package Pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page класс поисковой страницы https://yandex.ru/
 */
@SuppressWarnings("FieldCanBeLocal")
public class PageSberAstMain extends BasePage {
    /**
     * title базовой страницы Яндекс
     */
    private final String TITLE_SBER_AST = "Сбербанк-АСТ - электронная торговая площадка";
    /**
     * xPath клик-иконки Яндекс Маркета
     */
    private final String XPATH_SEARCH_FIELD = "//div[@class='default_search_border']//input";
    private final String XPATH_PROGRESS = "//div[@id='ajax-background']";
    private final String XPATH_RESULTS = "//div[@id='resultTable']//div[@content='node:hits']";
    private final String XPATH_ITEM_AMOUNT = ".//span[@class='es-el-amount']";
    private final String XPATH_ITEM_CURRENCY = ".//span[@class='es-el-currency']";
    private final String XPATH_ITEM_LAW = ".//span[@class='es-el-source-term']";
    private final String XPATH_ITEM_NAME = ".//span[@class='es-el-name']";
    private final String XPATH_ITEM_ORG = ".//div[@class='es-el-org-name']";
    private final String XPATH_ITEM_CODE = ".//span[@class='es-el-code-term']";
    private final String XPATH_NEXT_PAGE_BUTTON = "//div[@id='pager']//span[text()='>']";

    protected List<Map<String, String>> listAll = new ArrayList<>();

    /**
     * Шаг Проверить title страницы
     * @param step номер шага для аллюра
     * @return свой PO
     */
    public PageSberAstMain checkSberAstTitle(int step) {
        checkTitleFragment(step, TITLE_SBER_AST);
        return this;
    }

    @Step("step {step}. ")  // step
    public PageSberAstMain inputSearchField(int step, String text) {
        $x(XPATH_SEARCH_FIELD).shouldBe(visible, enabled).setValue(text).pressEnter();
        return this;
    }

    @Step("step {step}. ")  // step
    public PageSberAstMain collectAllPageResults(int step, double price, String currency, String law,
                                                 int maxCountView, int maxCountChoice) {
        int countView = 0;
        int nextPageNumber = 1;
        do {
            step++;
            nextPageNumber++;
            List<Map<String, String>> list = collectPageResults(step);
            countView += list.size();
            list.stream()
                    .filter(el -> el.get("currency").equals(currency)
                            && el.get("law").contains(law)
                            && Double.parseDouble(el.get("price")) > price)
                    .forEach(el -> {
                        if (listAll.size() < maxCountChoice) listAll.add(el);
                    });
        } while (listAll.size() < maxCountChoice && countView < maxCountView && clickNextPage(nextPageNumber));
        Assertions.assertEquals(maxCountChoice, listAll.size(),
                "Не найдено заданное количество требуемых позиций");
        listAll.forEach(el-> System.out.println(el));
        return this;
    }

    @Step("step {step}. ")  // step
    public List<Map<String, String>> collectPageResults(int step) {
        waitEndProgress();
        List<Map<String, String>> list = new ArrayList<>();
        $$x(XPATH_RESULTS)
                .shouldBe(sizeGreaterThan(0))
                .asDynamicIterable()
                .forEach(el-> {
                    list.add(Map.of("price", el.$x(XPATH_ITEM_AMOUNT).getText().replace(" ", ""),
                            "currency", el.$x(XPATH_ITEM_CURRENCY).getText(),
                            "law", el.$x(XPATH_ITEM_LAW).getText(),
                            "name", el.$x(XPATH_ITEM_NAME).getText(),
                            "org", el.$x(XPATH_ITEM_ORG).getText(),
                            "code", el.$x(XPATH_ITEM_CODE).getText()));
                });
        return list;
    }

    public void waitEndProgress() {
        $x(XPATH_PROGRESS)
                .shouldBe(exist, attribute("style", "display: none;"));
    }

    public boolean clickNextPage(int nextNumber) {
        String attr = $x(XPATH_NEXT_PAGE_BUTTON).shouldBe(exist).getAttribute("content");
        if (attr!=null && nextNumber == Integer.parseInt(attr)) {
            $x(XPATH_NEXT_PAGE_BUTTON).shouldBe(visible, enabled).click();
            return true;
        }
        return false;
    }

//    /**
//     * Шаг Перейти по иконке на Яндекс Маркет
//     * @param step номер шага для аллюра
//     * @return свой PO
//     */
//    @Step("step {step}. Перейти по иконке на Яндекс Маркет")  // step 3
//    public PageSberAstMain clickYandexMarketAndSwitch(String step) {
//        waitRealClick($x(XPATH_ICON_YANDEX_MARKET).shouldBe(visible, enabled));
//        switchTo().window(TITLE_YANDEX_MARKET);
//        return this;
//    }
}
