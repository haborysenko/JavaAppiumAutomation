package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

public class iOSTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/";

    @Override
    protected void setUp() throws Exception {

        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 14");
        capabilities.setCapability("platformVersion", "16.4");
        capabilities.setCapability("app", "/Users/halynab/courses/JavaAppiumAutomation/apks/Wikipedia.app");
        capabilities.setCapability("automationName", "XCUITest");
    }

    @Override
    protected void tearDown() throws Exception {

        driver.quit();

        super.tearDown();
    }
}
