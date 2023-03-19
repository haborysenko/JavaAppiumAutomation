import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.sql.Driver;
import java.util.List;

public class FirstTest {

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
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' element"
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "Cannot find element with text 'Object-oriented programming language'",
                15
        );
    }


    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input");

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find 'Search…' input to enter search query"
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find 'Search…' input to clear",
                30
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'X' to close search",
                30
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "'X' is still shown, means that we don't return on Home page"
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' element"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "Cannot find element with text 'Object-oriented programming language'",
                15
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "Unexpected title is returned",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSwipeArticle() {
        String search_query = "Appium";

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_query,
                "Cannot find 'Search…' element"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'" + search_query + "')]"),
                "Cannot find element with text " + search_query,
                15
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of articles while swipe",
                10
        );
    }

    @Test
    public void saveFirstArticleToMyList() {
        String folder_name = "Learning programming";

        //open app
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        //enter search query
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' element"
        );

        //open specific article with text 'Object-oriented programming language'
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "Cannot find element with text 'Object-oriented programming language'",
                15
        );

        //wait till the article title is shown
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        //click to add to reading list
        waitForElementAndClick(
                //By.xpath("//*[@text='Add to reading list']"),
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView"),
                "Cannot find option to add article to reading list",
                5
        );

        //create reading list
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button",
                5
        );

        //clear default name
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        //enter custom name for reading list
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folder_name,
                "Cannot put text into articles folder input"
        );

        //submit creation
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );

        //close article
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article by 'X'",
                5
        );

        //open my list to check created folder
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']/android.widget.ImageView"),
                "Cannot find navigation button to 'My lists'",
                5
        );

        //open folder
        waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find created folder",
                5
        );

        //swipe to delete article
        swipeElementToLeft(
                //By.xpath("//*[@text='Java (programming language)']"),
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[1]"),
                "Cannot find saved article"
        );

        //check that there is no more article
        waitForElementNotPresent(
                //By.xpath("//*[@text='Java (programming language)']"),
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[1]"),
                "Cannot delete saved article"
        );

        //open more options for folder
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        //remove folder
        waitForElementAndClick(
                By.xpath("//*[@text='Delete list']"),
                "Cannot find button to open article options",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        //open app and click in search input
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        //enter search query
        String search_query = "Linking park discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_query,
                "Cannot find 'Search…' element"
        );

       //check that some element present
       //String search_results_locator_xpath = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[resource-id='org.wikipedia:id/page_list_item_title']";
       String search_results_locator_id = "org.wikipedia:id/page_list_item_title";

       waitForElementPresent(
               By.id(search_results_locator_id),
               "Cannot find anything by the request "+search_query,
               15
       );

       //get number of elements
       int amount_of_search_results = getAmountOfElements(
               By.id(search_results_locator_id)
       );

       //verify that amount is > 0
        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results>0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        //open app and click in search input
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        //enter search query
        String search_line = "ewewewewew";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find 'Search…' element"
        );

        String search_results_locator_id = "org.wikipedia:id/page_list_item_title";
        String empty_result_label_locator_xpath = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label_locator_xpath),
                "Cannot find empty result label by the request "+ search_line,
                15
        );

        assertElementNotPresent(
                By.id(search_results_locator_id),
                "We've found some results by the request "+ search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        //if test failed in the middle we have to rotate here
        //driver.rotate(ScreenOrientation.PORTRAIT);

        //open app and click in search input
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        //enter search query
        String search_article_by_request = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_article_by_request,
                "Cannot find search input"
        );

        //open specific article with text 'Object-oriented programming language'
        String  article_name_in_search_list = "Object-oriented programming language";

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'" + article_name_in_search_list + "')]"),
                "Cannot find element with text " + article_name_in_search_list,
                30
        );

        //save title before rotation
        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        //rotate screen
        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title has been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        //rotate screen
        driver.rotate(ScreenOrientation.PORTRAIT);
        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title has been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSerachArticleInBackground() {
        //open app
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' element"
        );

        //enter search query
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find 'Search…' element"
        );

        //open specific article with text 'Object-oriented programming language'
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "Cannot find element with text 'Object-oriented programming language'",
                15
        );

        //send app in background for duration 2 sec
        driver.runAppInBackground(2);

        //verify that after article still present
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Object-oriented programming language')]"),
                "Cannot find article after returning from background",
                15
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
                10);

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
        if (amount_of_elements>0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}
