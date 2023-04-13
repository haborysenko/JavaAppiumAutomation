package tests.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.NavigationUI;
import tests.android.AndroidNavigationUI;
import tests.ios.iOSNavigationUI;

public class NavigationUIFactory {

    public static NavigationUI get(AppiumDriver driver) {
        if(Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUI(driver);
        } else {
            return new iOSNavigationUI(driver);
        }
    }
}
