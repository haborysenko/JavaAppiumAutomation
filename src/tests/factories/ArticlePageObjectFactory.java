package tests.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.ArticlePageObject;
import tests.android.AndroidArticlePageObject;
import tests.ios.iOSArticlePageObject;

public class ArticlePageObjectFactory {

    public static ArticlePageObject get(AppiumDriver driver) {
        if(Platform.getInstance().isAndroid()) {
            return new AndroidArticlePageObject(driver);
        } else {
            return new iOSArticlePageObject(driver);
        }
    }
}
