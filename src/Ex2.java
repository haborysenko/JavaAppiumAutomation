import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class Ex2 {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/halynab/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void verifyExpectedTestUsingAssert() {
        WebElement search_input = waitForElementPresent(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        String actual_value_of_search_placeholder = search_input.getAttribute("text");
        String expected_value_of_search_placeholder = "Search Wikipedia";

        Assert.assertEquals(
                "Unexpected placeholder is returned",
                expected_value_of_search_placeholder,
                actual_value_of_search_placeholder
        );
    }

    @Test
    public void verifyExpectedTextUsingAssertElementHasText() {
        String expected_value_of_search_placeholder = "Search Wikipedia";

        assertElementHasText(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                expected_value_of_search_placeholder,
                "Unexpected placeholder is returned"
        );
    }

    @Test
    public void verifyExpectedTextContainedInElement() {
        String expected_value_of_search_placeholder = "Search Wikipedia";

        textToBePresentInElementLocated(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                expected_value_of_search_placeholder);
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, "Element is not present", 10);
    }

    private boolean textToBePresentInElementLocated(By by, String expected_value) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(by, expected_value));
        return false;
    }

    private boolean assertElementHasText(By by, String expected_value, String error_message) {
        WebElement element = waitForElementPresent(by);
        String actual_value = element.getAttribute("text");
        Assert.assertEquals(error_message, expected_value, actual_value);
        return false;
    }
}