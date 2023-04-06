package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEARN_MORE_ABOUT_WIKIPEDIA_LINK = "//XCUIElementTypeStaticText[@name=\"Узнать подробнее о Википедии\"]",
            STEP_NEW_WAYS_TO_EXPLORE_TITLE = "Новые способы изучения",
            STEP_ADD_OR_EDIT_PREFERRED_LANGS_LINK = "//XCUIElementTypeStaticText[@name=\"Добавить или изменить предпочтительные языки\"]",
            STEP_LEARN_MORE_ABOUT_DATE_COLLECTED_LINK = "//XCUIElementTypeStaticText[@name=\"Узнать подробнее о сборе данных\"]",
            NEXT_LINK = "//XCUIElementTypeStaticText[@name=\"Далее\"]",
            GET_STARTED_BUTTON = "//XCUIElementTypeStaticText[@name=\"Начать\"]";

    public WelcomePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(
                By.xpath(STEP_LEARN_MORE_ABOUT_WIKIPEDIA_LINK),
                "Cannot find element 'Узнать подробнее о Википедии'"
        );
    }

    public void waitForNewWaysToExploreText() {
        this.waitForElementPresent(
                By.id(STEP_NEW_WAYS_TO_EXPLORE_TITLE),
                "Cannot find element 'Новые способы изучения'"
        );
    }

    public void waitForAddOrEditPreferredLangsLink() {
        this.waitForElementPresent(
                By.id(STEP_ADD_OR_EDIT_PREFERRED_LANGS_LINK),
                "Cannot find element 'Добавить или изменить предпочтительные языки'"
        );
    }

    public void waitForAboutDataCollectedLink() {
        this.waitForElementPresent(
                By.id(STEP_LEARN_MORE_ABOUT_DATE_COLLECTED_LINK),
                "Cannot find element 'Узнать подробнее о сборе данных'"
        );
    }

    public void clickNextButton() {
        this.waitForElementAndClick(
                By.xpath(NEXT_LINK),
                "Cannot find button 'Далее"
        );
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(
                By.xpath(GET_STARTED_BUTTON),
                "Cannot find button 'Далее"
        );
    }
}
