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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class Ex5 {

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
    public void testSaveTwoArticlesAndRemoveFirstAfterwards() {
        String search_query = "Appium";
        String name_for_folder = "About Appium";

        // 1. Save the first two search results to a new folder with a specified name.
        for (int i = 0; i < 2; i++) {
            // wait for search input and click on it
            waitForElementAndClick(
                    By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                    "Cannot find 'Search Wikipedia' element",
                    20
            );

            // enter search query in field with placeholder 'Search…'
            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text,'Search…')]"),
                    search_query,
                    "Cannot find 'Search…' element"
            );

            // save search results titlels in string array, where each element is titel of article
            List<String> titels = waitForElementsAndGetAttributeValues(
                    By.id("org.wikipedia:id/page_list_item_title"),
                    "text",
                    "Cannot find article items",
                    30
            );

            // get title of article 'i'
            String title = titels.get(i);

            // open 'i' article
            waitForElementAndClick(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text,'" + title + "')]"),
                    "Cannot find element with text " + title,
                    30
            );

            // wait till title is shown, means screen loaded
            waitForElementPresent(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "Cannot find article title",
                    30
            );

            // init adding to reading list
            waitForElementAndClick(
                    By.xpath("//android.widget.ImageView[@content-desc='Add this article to a reading list']"),
                    "Cannot find option to add article to reading list",
                    30
            );

            // for the first article (i=0) we have to create new folder
            if (i == 0) {
                // click 'Got it' button
                waitForElementAndClick(
                        By.id("org.wikipedia:id/onboarding_button"),
                        "Cannot find 'Got it' button",
                        30
                );

                // clear default name
                waitForElementAndClear(
                        By.id("org.wikipedia:id/text_input"),
                        "Cannot find input to set name of articles folder",
                        30
                );

                // enter custom name for reading list
                waitForElementAndSendKeys(
                        By.id("org.wikipedia:id/text_input"),
                        name_for_folder,
                        "Cannot put text into articles folder input"
                );

                // submit creation
                waitForElementAndClick(
                        By.xpath("//*[@text='OK']"),
                        "Cannot find 'OK' button",
                        30
                );

                // for the second article we will add to existing folder
            } else {
                // add to folder with specified above name
                waitForElementAndClick(
                        By.xpath("//*[@text='" + name_for_folder + "']"),
                        "Cannot find " + name_for_folder,
                        30
                );
            }

            // close each article
            waitForElementAndClick(
                    By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                    "Cannot close article by 'X'",
                    30
            );
        }

        // 2. Open a reading list to check the articles in a folder. Remove the first one and check the second one. Verify the number of articles before and after removal.
        // open my list
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to 'My lists'",
                60
        );

        // open folder
        waitForElementAndClick(
                By.id("org.wikipedia:id/item_title"),
                "Cannot find created folder " + name_for_folder,
                60
        );

        // save titlels in string array, where each element is titel of article in created folder. we need it to remove article further
        List<String> titels = waitForElementsAndGetAttributeValues(
                By.id("org.wikipedia:id/page_list_item_title"),
                "text",
                "Cannot find article items",
                30
        );

        // get number of elements before delete
        int amount_of_search_results_before_delete = getAmountOfElements(
                By.id("org.wikipedia:id/page_list_item_container")
        );

        // verify that amount is 2
        Assert.assertTrue(
                "Incorrect amount before delete",
                amount_of_search_results_before_delete == 2);

        // swipe to delete the first article
        swipeElementToLeft(
                By.xpath("//*[@text='" + titels.get(0) + "']"),
                "Cannot find saved article"
        );

        // get number of elements after delete
        int amount_of_search_results_after_delete = getAmountOfElements(
                By.id("org.wikipedia:id/page_list_item_container")
        );

        // verify that amount is 1
        Assert.assertTrue(
                "Incorrect amount after delete",
                amount_of_search_results_after_delete == 1);

        //3. Open article that is left and verify title
        // click to open article
        waitForElementAndClick(
                By.xpath("//*[@text='" + titels.get(1) + "']"),
                "Cannot find element with text " + titels.get(1),
                30
        );

        // get for title of opened article text attribute
         String article_title = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                 "text",
                 "Cannot find article title",
                 30
        );

        // verify that titels are the same
         Assert.assertEquals(
                 "Titles are not the same",
                 article_title,
                 titels.get(1)
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

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private boolean waitForElementNotPresent(By by, String error_message) {
        return waitForElementNotPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
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
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
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
    public void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;

        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up.\n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                20);

        int left_x = element.getLocation().getX(); //the leftmost point of the element
        int right_x = left_x + element.getSize().getWidth(); //the rightmost point of the element
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
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

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    /*
    This is a method that waits for all elements matching a given By locator to be present on the page, retrieves a specified attribute value for each element, and returns a list of the attribute values.
    Here's a breakdown of what the method does:
    1. It calls the waitForAllElementsPresent() method, passing in the By locator, error message, and timeout duration. This method waits for all elements matching the locator to be present on the page and returns a list of WebElement objects.
    2. It creates a new ArrayList called listOfAttributeValues to store the attribute values of each element.
    3. It loops through each WebElement in the elements list and retrieves the value of the specified attribute using the getAttribute() method. It then adds this value to the listOfAttributeValues.
    4. Finally, it returns the listOfAttributeValues containing the attribute values for each element.
    You can call this method with a By locator, attribute name, error message, and timeout duration to get a list of attribute values for all matching elements on the page.
     */
    private List<String> waitForElementsAndGetAttributeValues(By by, String attribute, String error_message, long timeoutInSeconds) {
        List<WebElement> elements = waitForAllElementsPresent(by, error_message, timeoutInSeconds);

        List<String> listOfAttributeValues = new ArrayList<String>();
        for (WebElement element : elements) {
            listOfAttributeValues.add(element.getAttribute(attribute));
        }
        return listOfAttributeValues;
    }
}