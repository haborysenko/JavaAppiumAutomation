package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import org.junit.Test;
import tests.factories.ArticlePageObjectFactory;
import tests.factories.MyListsPageObjectFactory;
import tests.factories.NavigationUIFactory;
import tests.factories.SearchPageObjectFactory;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {
        String search_line = "Java";
        String search_result_to_click_on = "Object-oriented programming language";
        String folder_name = "Learning programming";
        String article_title_substring = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(search_result_to_click_on);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElementWithSubstring(article_title_substring);
        String article_title = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyNewList(folder_name);

        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isIOS()){
            MyListsPageObject.clickToCloseLoginSuggestion();
        }else {
            MyListsPageObject.openFolderByName(folder_name);
        }
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesAndRemoveFirstAfterwards() {
        String search_line = "Appium";
        String folder_name = "About Appium";
        String article_title_substring = "Appi"; //cannot get article title for iOS without substring

        // 1. Search by line and save titles with search query substring in list.
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        List<String> titles_in_search_results = SearchPageObject.getArticleTitlesFromSearchResults(article_title_substring);

        // 2. Save the first two search results to a new folder with a specified name.
        for (int i = 0; i < 2; i++) {
            // get title of article 'i',  open it & wait till title is shown
            String title = titles_in_search_results.get(i);
            SearchPageObject.clickByArticleWithSubstring(title);
            ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
            ArticlePageObject.waitForTitleElementWithSubstring(article_title_substring);

            // for Android if the first article (i=0) we have to create new folder, for the second - add to existing one
            // for iOS add to my saved
            if(Platform.getInstance().isAndroid() && i == 0){
                ArticlePageObject.addArticleToMyNewList(folder_name);
            } else if (Platform.getInstance().isAndroid() && i != 0) {
                ArticlePageObject.addArticleToMyExistingList(folder_name);
            } else {
                ArticlePageObject.addArticlesToMySaved();
            }
            ArticlePageObject.closeArticle();

            // for iOS after close article search query still is in the search field, for Android we have to enter again
            if(Platform.getInstance().isAndroid()) {
                SearchPageObject.initSearchInput();
                SearchPageObject.typeSearchLine(search_line);
            }
        }

        // 3. Remove the first article from reading list and verifying the number of articles before and after removal.
        if(Platform.getInstance().isIOS()){
            SearchPageObject.clickCancelSearch();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isIOS()){
            MyListsPageObject.clickToCloseLoginSuggestion();
        }else {
            MyListsPageObject.openFolderByName(folder_name);
        }
        List<String> titles_in_my_list_before_delete = MyListsPageObject.getArticleTitlesFromMyList(article_title_substring);

        // verify that amount of articles before delete is 2
        int amount_of_search_results_before_delete = titles_in_my_list_before_delete.size();
        assertEquals(
                "Incorrect amount before delete",
                2,
                amount_of_search_results_before_delete);

        // remove the first one
        MyListsPageObject.swipeByArticleToDelete(titles_in_my_list_before_delete.get(0));

        // verify that amount of articles after delete is 1
        List<String> titles_in_my_list_after_delete = MyListsPageObject.getArticleTitlesFromMyList(article_title_substring);
        int amount_of_search_results_after_delete = titles_in_my_list_after_delete.size();
        assertEquals(
                "Incorrect amount after delete",
                1,
                amount_of_search_results_after_delete);

        // 4. Open article that is left and verify that title before and after removal are the same
        MyListsPageObject.clickByArticleWithSubstring(titles_in_my_list_before_delete.get(1));
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Titles are not the same",
                article_title,
                titles_in_my_list_before_delete.get(1)
        );
    }
}
