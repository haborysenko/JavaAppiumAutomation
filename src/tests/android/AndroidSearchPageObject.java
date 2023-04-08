package tests.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
                SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
                SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
                SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
                SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]";
                SEARCH_RESULT_TITLE_DESCRIPTION_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]";
                SEARCH_RESULT_ELEMENT = "id:org.wikipedia:id/page_list_item_title";
                SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
    }

    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
