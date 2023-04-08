package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import tests.factories.SearchPageObjectFactory;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResults("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String search_line = "Linking park discography";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        int actual_amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found too few results",
                actual_amount_of_search_results > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        String search_line = "ewewewewew";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchResultsByStringAndCancelSearch() {
        String search_line = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.assertThatEachTitleInSearchResultsContainSearchLine(search_line);
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchPlaceholderHasExactExpectedTextWithUsingSimpleAssert() {
        String expected_value_of_search_placeholder = "Search Wikipedia";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        String actual_value_of_search_placeholder = SearchPageObject.getSearchInitInputPlaceholderText();
        assertEquals(
                "Unexpected placeholder is returned",
                expected_value_of_search_placeholder,
                actual_value_of_search_placeholder
        );
    }

    @Test
    public void testSearchPlaceholderHasExactExpectedText() {
        String expected_value_of_search_placeholder = "Search Wikipedia";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.assertSearchInitInputPlaceholderHasExactText(expected_value_of_search_placeholder);
    }

    @Test
    public void testSearchPlaceholderContainsExpectedText() {
        String expected_value_of_search_placeholder = "Search Wikipedia";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.assertSearchInitInputPlaceholderContainsText(expected_value_of_search_placeholder);
    }
}
