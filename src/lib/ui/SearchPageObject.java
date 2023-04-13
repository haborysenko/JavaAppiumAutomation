package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;
import static org.hamcrest.CoreMatchers.containsString;

abstract public class SearchPageObject extends MainPageObject{

    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULT_TITLE_DESCRIPTION_BY_SUBSTRING_TPL,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT;

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementTitleDescription(String substring_title, String substring_description) {
        return SEARCH_RESULT_TITLE_DESCRIPTION_BY_SUBSTRING_TPL
                .replace("{SUBSTRING_TITLE}", substring_title)
                .replace( "{SUBSTRING_DESCRIPTION}", substring_description);
    }
    /*TEMPLATE METHODS*/

    public void initSearchInput() {
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                 "Cannot find init search element with placeholder 'Search Wikipedia'");
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find & click in init search element with placeholder 'Search Wikipedia'");
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button 'X'");
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button 'X' is still present");
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot fins and click search cancel button");
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input element");
    }

    public void clearSearchInputField() {
        this.waitForElementAndClearTextField(
                SEARCH_INPUT,
                "Cannot clear search input");
    }

    public void waitForSearchResults(String substring) {
        String search_result_element_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_element_xpath,
                "Cannot find search result with substring " + substring,
                30);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_element_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_element_xpath,
                "Cannot find and click search result with substring " + substring,
                30);
    }

    public int getAmountOfFoundArticles() {
        //wait till at leas one is shown
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything",
                15
        );

        //get amount of elements
        return this.getAmountOfElements(
                SEARCH_RESULT_ELEMENT
        );
    }

    public void assertThatEachTitleInSearchResultsContainSearchLine(String search_line) {
        List<String> titles = this.getArticleTitlesFromSearchResults(search_line);

        for(String title: titles){
            Assert.assertThat(title, containsString(search_line));
        }
    }

    public List<String> getArticleTitlesFromSearchResults(String substring) {
        String search_result_element_xpath = getResultSearchElement(substring);
                if(Platform.getInstance().isAndroid()) {
                    return this.waitForElementsAndGetAttribute(
                            search_result_element_xpath,
                            "text",
                            "Cannot get article title attribute",
                            30);
                } else {
                    return this.waitForElementsAndGetAttribute(
                            search_result_element_xpath,
                            "name",
                            "Cannot get article title attribute",
                            30);
                }
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                30
        );
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We've found some results, but not suppose");
    }

    public WebElement waitForSearchInitInput() {
        return this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Cannot find search input",
                30);
    }

    public String getSearchInitInputPlaceholderText() {
        WebElement search_placeholder = waitForSearchInitInput();
        if(Platform.getInstance().isAndroid()) {
            return search_placeholder.getAttribute("text");
        } else {
            return search_placeholder.getAttribute("name"); //label
        }
    }

    public void assertSearchInitInputPlaceholderHasExactText(String expected_placeholder) {
        this.assertElementHasExactText(
                SEARCH_INIT_ELEMENT,
                expected_placeholder,
                "Init search placeholder does not have exact text " + expected_placeholder);
    }

    public void assertSearchInitInputPlaceholderContainsText(String expected_placeholder) {
        this.textToBePresentInElementLocated(
                SEARCH_INIT_ELEMENT,
                expected_placeholder);
    }
}

