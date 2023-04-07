package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    /*
    The method takes three arguments:

    by - a locator strategy that specifies how to locate the element on the screen.
    error_message - a custom error message to be displayed if the element is not found within the specified timeout.
    timeoutInSeconds - the amount of time, in seconds, to wait for the element to appear on the screen before timing out.
    The method creates a new WebDriverWait instance with the specified timeout and error message. Then, it waits until the expected condition (in this case, the presence of the element located by the By strategy) is met. If the element is found within the specified timeout, the method returns that element.

    If the element is not found within the specified timeout, the method throws a TimeoutException with the specified error message.

    Overall, this method helps to ensure that the Appium test script waits for an element to appear on the screen before trying to interact with it, which can help prevent errors and improve the reliability of the test.
     */
    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(
                locator,
                error_message,
                30);
    }

    public WebElement waitForElementPresent(String locator) {
        return waitForElementPresent(locator,
                "Element is not present",
                10);
    }

    public List<WebElement> waitForAllElementsPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndClick(String locator, String error_message) {
        return waitForElementAndClick(locator, error_message, 10);
    }


    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message) {
        WebElement element = waitForElementPresent(locator, error_message, 10);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public boolean waitForElementNotPresent(String locator, String error_message) {
        return waitForElementNotPresent(locator, error_message, 10);
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = (size.width / 2);
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    /*
    This Java code defines a method called "swipeUpToFindElement" which takes three parameters:
    -"by" of type "By" which represents the locator strategy used to find an element
    -"error_message" of type "String" which represents the error message to be displayed if the element is not found
    -"max_swipes" of type "int" which represents the maximum number of swipes to be performed before giving up on finding the element.

    The method uses a while loop to repeatedly perform a swipe up action until the element identified by the "by" parameter is found or the maximum number of swipes is reached. If the element is not found after the maximum number of swipes, the method displays an error message using the "waitForElementPresent" method and returns.
    If the element is found, the method returns without displaying any error message.
     */
    public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
        By by = this.getLocatorByString(locator);
        int already_swiped = 0;

        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(
                        locator,
                        "Cannot find element by swiping up.\n" + error_message,
                        5);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeElementToLeft(String locator, String error_message) {
        WebElement element = waitForElementPresent(
                locator,
                error_message,
                10);

        int left_x = element.getLocation().getX(); //the leftmost point of the element
        int right_x = left_x + element.getSize().getWidth(); //the rightmost point of the element
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release()
                .perform();
    }

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String error_message) {
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }


    public void assertElementPresent(String locator, String error_message) {
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements <= 0) {
            String default_message = "An element '" + locator + "' supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public List<String> waitForElementsAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds) {
        List<WebElement> elements = waitForAllElementsPresent(locator, error_message, timeoutInSeconds);

        List<String> attributes = new ArrayList<>();
        for (WebElement element : elements) {
            attributes.add(element.getAttribute(attribute));
        }
        return attributes;
    }

    public boolean assertElementHasExactText(String locator, String expected_value, String error_message) {
        WebElement element = waitForElementPresent(locator);
        String actual_value = element.getAttribute("text");
        Assert.assertEquals(error_message, expected_value, actual_value);
        return false;
    }

    public boolean textToBePresentInElementLocated(String locator, String expected_value) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(by, expected_value));
        return false;
    }

    private By getLocatorByString(String locator_with_type) {
        String [] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if(by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }

    public void tapByCoordinate(int x, int y) {
        // Create a TouchAction object
        TouchAction action = new TouchAction(driver);

        // Perform the tap action on the specified coordinates
        action.tap(PointOption.point(x, y)).perform();
    }
}
