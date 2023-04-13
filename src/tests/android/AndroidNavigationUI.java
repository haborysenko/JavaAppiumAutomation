package tests.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {
    static  {
        MY_LISTS_LINK = "id:Saved";
    }

    public AndroidNavigationUI (AppiumDriver driver) {
        super(driver);
    }
}
