package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import java.util.List;

public class MyListsPageObject extends MainPageObject {

    private static final String
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']",
            ARTICLE_TITLE = "org.wikipedia:id/page_list_item_title";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String getFolderXpathByName(String folder_name) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    /*TEMPLATE METHODS*/

    public void openFolderByName(String folder_name) {
        String folder_name_xpath = getFolderXpathByName(folder_name);
        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find folder by name " + folder_name,
                30
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                By.xpath(article_title_xpath),
                "Cannot find saved article by title " + article_title
        );
    }

    public List<String> getArticleTitlesFromMyList() {
        return this.waitForElementsAndGetAttribute(
                By.id(ARTICLE_TITLE),
                "text",
                "Cannot get article title attribute",
                30);
    }

    public int getAmountOfFoundArticles() {
        //wait till at leas one is shown
        this.waitForElementPresent(
                By.id(ARTICLE_TITLE),
                "Cannot find anything",
                15
        );

        //get amount of elements
        return this.getAmountOfElements(
                By.id(ARTICLE_TITLE)
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_title_xpath = getFolderXpathByName(article_title);
        this.waitForElementNotPresent(
                By.xpath(article_title_xpath),
                "Cannot delete saved article with title " + article_title
        );
    }

    public void swipeByArticleToDelete(String article_title) {
        String article_title_xpath = getFolderXpathByName(article_title);
        this.waitForArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(
                By.xpath(article_title_xpath),
                "Cannot find saved article with title " + article_title
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void clickByArticleWithSubstring(String substring) {
        String article_title_element_xpath = getSavedArticleXpathByTitle(substring);
        this.waitForElementAndClick(
                By.xpath(article_title_element_xpath),
                "Cannot find and click article title with substring " + substring,
                30);
    }
}
