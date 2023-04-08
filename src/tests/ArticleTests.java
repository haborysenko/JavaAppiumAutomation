package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import tests.factories.ArticlePageObjectFactory;
import tests.factories.SearchPageObjectFactory;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {


        String search_line = "Java";
        String search_result_to_click_on = "Object-oriented programming language";
        String expected_article_title = "Java (programming language)";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(search_result_to_click_on);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String actual_article_title = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Unexpected title is returned",
                expected_article_title,
                actual_article_title
        );
    }

    @Test
    public void testSwipeArticle() {
        String search_query = "Appium";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.clickByArticleWithSubstring(search_query);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testArticleTitlePresent() {
        String search_query = "Appium";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_query);
        SearchPageObject.clickByArticleWithSubstring(search_query);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.assertThereIsTitleOfArticle();
    }
}