package tests.iOS;

import lib.CoreTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
    public void testPassThoughWelcome() {
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();

        WelcomePage.waitForNewWaysToExploreText();
        WelcomePage.clickNextButton();

        WelcomePage.waitForAddOrEditPreferredLangsLink();
        WelcomePage.clickNextButton();


        WelcomePage.waitForAboutDataCollectedLink();
        WelcomePage.clickGetStartedButton();
    }

    @Test
    public void testWaitForFreeEncyclopedia() {
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForFreeEncyclopedia();
    }
}
