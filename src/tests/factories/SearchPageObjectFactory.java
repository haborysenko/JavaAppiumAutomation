package tests.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.SearchPageObject;
import tests.android.AndroidSearchPageObject;
import tests.ios.iOSSearchPageObject;

public class SearchPageObjectFactory {

    public static SearchPageObject get(AppiumDriver driver) {
        if(Platform.getInstance().isAndroid()) {
            return new AndroidSearchPageObject(driver);
        } else {
            return new iOSSearchPageObject(driver);
        }
    }
}
