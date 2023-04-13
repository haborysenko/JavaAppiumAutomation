package tests.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListPageObjects extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{TITLE}')]";
        ARTICLE_TITLE = "xpath://XCUIElementTypeStaticText[contains(@name,'')]";
        ACTION_DELETE = "id:swipe action delete";
        CLOSE_LOGIN_SUGGESTION = "id:Close";
    }

    public iOSMyListPageObjects(AppiumDriver driver) {
        super(driver);
    }
}
