import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Ex6 {

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
    public void testArticleTitlePresent() {
        String search_query = "Appium";

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element",
                20
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_query,
                "Cannot find 'Search…' element"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'" + search_query + "')]"),
                "Cannot find element with text " + search_query,
                30
        );

        // wait till title is shown, test doesn't work without it
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30
        );

        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title is not found"
        );
    }

    //METHODS
    /*
    The method takes three arguments:

    by - a locator strategy that specifies how to locate the element on the screen.
    error_message - a custom error message to be displayed if the element is not found within the specified timeout.
    timeoutInSeconds - the amount of time, in seconds, to wait for the element to appear on the screen before timing out.
    The method creates a new WebDriverWait instance with the specified timeout and error message. Then, it waits until the expected condition (in this case, the presence of the element located by the By strategy) is met. If the element is found within the specified timeout, the method returns that element.

    If the element is not found within the specified timeout, the method throws a TimeoutException with the specified error message.

    Overall, this method helps to ensure that the Appium test script waits for an element to appear on the screen before trying to interact with it, which can help prevent errors and improve the reliability of the test.
     */
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
        return waitForElementPresent(by, "Element is not present", 5);
    }

    private List<WebElement> waitForAllElementsPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndClick(By by, String error_message) {
        return waitForElementAndClick(by, error_message, 10);
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 5);
        element.sendKeys(value);
        return element;
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private void assertElementPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements <= 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }
}