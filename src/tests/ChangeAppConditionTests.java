package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        String search_line = "Java";
        String search_result_to_click_on = "Object-oriented programming language";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(search_result_to_click_on);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title_before_rotation = ArticlePageObject.getArticleTitle();

        this.rotateScreenLandscape();
        String article_title_after_rotation = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Article title has been changed after screen rotation",
                article_title_before_rotation,
                article_title_after_rotation
        );

        this.rotateScreenPortrait();
        String article_title_after_second_rotation = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Article title has been changed after screen rotation",
                article_title_before_rotation,
                article_title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        String search_line = "Java";
        String article_text_to_wait_for = "Object-oriented programming language";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResults(article_text_to_wait_for);

        this.backgroundApp(5);
        SearchPageObject.waitForSearchResults(article_text_to_wait_for);
    }
}
