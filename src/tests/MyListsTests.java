package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import java.util.List;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {
        String search_line = "Java";
        String search_result_to_click_on = "Object-oriented programming language";
        String folder_name = "Learning programming";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(search_result_to_click_on);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyNewList(folder_name);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(folder_name);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesAndRemoveFirstAfterwards() {
        String search_line = "Appium";
        String folder_name = "About Appium";

        // 1. Save the first two search results to a new folder with a specified name.
        for (int i = 0; i < 2; i++) {
            // wait for search input and click on it
            SearchPageObject SearchPageObject = new SearchPageObject(driver);
            SearchPageObject.initSearchInput();
            SearchPageObject.typeSearchLine(search_line);

            List<String> titles_in_search_results = SearchPageObject.getArticleTitlesFromSearchResults();

            // get title of article 'i',  open it & wait till title is shown
            String title = titles_in_search_results.get(i);
            SearchPageObject.clickByArticleWithSubstring(title);
            ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
            ArticlePageObject.waitForTitleElement();

            // for the first article (i=0) we have to create new folder, for the second - add to existing one
            if (i == 0) {
                ArticlePageObject.addArticleToMyNewList(folder_name);
            } else {
                ArticlePageObject.addArticleToMyExistingList(folder_name);
            }
            ArticlePageObject.closeArticle();
        }

        // 2. Remove the first article from reading list and verifying the number of articles before and after removal.
        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(folder_name);
        List<String> titles_in_my_list = MyListsPageObject.getArticleTitlesFromMyList();

        // verify that amount of articles before delete is 2
        int amount_of_search_results_before_delete = MyListsPageObject.getAmountOfFoundArticles();
        assertEquals(
                "Incorrect amount before delete",
                2,
                amount_of_search_results_before_delete);

        // remove the first one
        MyListsPageObject.swipeByArticleToDelete(titles_in_my_list.get(0));

        // verify that amount of articles after delete is 1
        int amount_of_search_results_after_delete = MyListsPageObject.getAmountOfFoundArticles();
        assertEquals(
                "Incorrect amount after delete",
                1,
                amount_of_search_results_after_delete);

        //3. Open article that is left and verify that title before and after removal are the same
        MyListsPageObject.clickByArticleWithSubstring(titles_in_my_list.get(1));
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Titles are not the same",
                article_title,
                titles_in_my_list.get(1)
        );
    }
}
