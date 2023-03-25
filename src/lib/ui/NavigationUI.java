package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{

    private final static String
        MY_LISTS_LINK = "//android.widget.FrameLayout[@content-desc='My lists']/android.widget.ImageView";


    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        this.waitForElementAndClick(
                By.xpath(MY_LISTS_LINK),
                "Cannot find navigation button to 'My lists'",
                5
        );
    }
}
