package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text,'Search…')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]",
        SEARCH_RESULT_ELEMENT = "org.wikipedia:id/page_list_item_title",
        SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /*TEMPLATE METHODS*/

    public void initSearchInput() {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find init search element with placeholder 'Search Wikipedia'");
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find & click in init search element with placeholder 'Search Wikipedia'");
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button 'X'");
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button 'X' is still present");
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot fins and click search cancel button");
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line,"Cannot find and type into search input element");
    }

    public void waitForSearchResults(String substring) {
        String search_result_element_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_element_xpath), "Cannot find search result with substring "+substring, 30);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_element_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_element_xpath), "Cannot find and click search result with substring "+substring, 30);
    }

    public int getAmountOfFountArticles() {
        //wait till at leas one is shown
        this.waitForElementPresent(
                By.id(SEARCH_RESULT_ELEMENT),
                "Cannot find anything",
                15
        );

        //get amount of elements
        return this.getAmountOfElements(
                By.id(SEARCH_RESULT_ELEMENT)
        );
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element",
                30
        );
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We've found some results, but not suppose");
    }
}

