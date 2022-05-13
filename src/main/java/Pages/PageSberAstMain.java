package Pages;

import io.qameta.allure.Step;

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
public class PageSberAstMain extends BasePage {
    /**
     * title базовой страницы Яндекс
     */
    public String TITLE_SBER_AST = "Сбербанк-АСТ - электронная торговая площадка";
    /**
     * xPath клик-иконки Яндекс Маркета
     */
    public String XPATH_SEARCH_FIELD = "//div[@class='default_search_border']//input";
    public String XPATH_PROGRESS = "//div[@id='ajax-background']";
    public String XPATH_RESULTS = "//div[@id='resultTable']//div[@content='node:hits']";
    public String XPATH_ITEM_AMOUNT = ".//span[@class='es-el-amount']";
    public String XPATH_ITEM_CURRENCY = ".//span[@class='es-el-currency']";
    public String XPATH_ITEM_LAW = ".//span[@class='es-el-source-term']";
    public String XPATH_ITEM_NAME = ".//span[@class='es-el-name']";
    public String XPATH_ITEM_ORG = ".//div[@class='es-el-org-name']";
    public String XPATH_ITEM_CODE = ".//span[@class='es-el-code-term']";

    /**
     * Шаг Проверить title страницы
     * @param step номер шага для аллюра
     * @return свой PO
     */
    public PageSberAstMain checkSberAstTitle(String step) {
        checkTitleFragment(step, TITLE_SBER_AST);
        return this;
    }

    @Step("step {step}. ")  // step
    public PageSberAstMain inputSearchField(String step, String text) {
        $x(XPATH_SEARCH_FIELD).shouldBe(visible, enabled).setValue(text).pressEnter();
        return this;
    }

    @Step("step {step}. ")  // step
    public PageSberAstMain collectPageResults(String step, double price, String currency, String law) {
        int count = 0;
        List<Map<String, String>> list = new ArrayList<>();
        waitEndProgress();
        $$x(XPATH_RESULTS)
                .shouldBe(sizeGreaterThan(0))
                .asDynamicIterable()
                .forEach(el-> {
                    list.add(Map.of("price", el.$x(XPATH_ITEM_AMOUNT).getText(),
                            "currency", el.$x(XPATH_ITEM_CURRENCY).getText(),
                            "law", el.$x(XPATH_ITEM_LAW).getText(),
                            "name", el.$x(XPATH_ITEM_NAME).getText(),
                            "org", el.$x(XPATH_ITEM_ORG).getText(),
                            "code", el.$x(XPATH_ITEM_CODE).getText()));
                });
        List<Map<String, String>> filteredList = list.stream()
                .filter(el-> el.get("currency").equals(currency)
                        && el.get("law").contains(law)
                        && Double.parseDouble(el.get("price").replace(" ", "")) > price)
                .collect(Collectors.toList());
        System.out.println(list.size());
        System.out.println(list);
        System.out.println(filteredList.size());
        System.out.println(filteredList);
        return this;
    }

    public void waitEndProgress() {
        $x(XPATH_PROGRESS)
                .shouldBe(exist, attribute("style", "display: none;"));
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
