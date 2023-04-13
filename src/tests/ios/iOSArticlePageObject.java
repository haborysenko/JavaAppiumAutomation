package tests.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        //TITLE = "xpath:(//XCUIElementTypeStaticText[@name='Java (programming language)'])[1]";
        TITLE = "xpath:(//XCUIElementTypeStaticText[contains(@name,'Appi')])[1]";
        TITLE_BY_SUBSTRING_TPL = "xpath:(//XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')])[1]";
        FOOTER_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='View article in browser']";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
    }

    public iOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
