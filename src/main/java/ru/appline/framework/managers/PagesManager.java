package ru.appline.framework.managers;

import ru.appline.framework.pages.BasePage;
import ru.appline.framework.pages.CartPages;
import ru.appline.framework.pages.SearchResultsPage;
import ru.appline.framework.pages.StartPage;

import java.util.HashMap;
import java.util.Map;

import static ru.appline.framework.entities.ListProducts.clearProducts;

public class PagesManager {

    private static PagesManager pagesManager;
    private static Map<String, BasePage> map = new HashMap<>();

    private PagesManager() {
    }

    public static void pagesIsNull() {
        map.clear();
        clearProducts();
    }

    public static PagesManager getManagerPages() {
        if (pagesManager == null) {
            pagesManager = new PagesManager();
        }
        return pagesManager;
    }

    public StartPage getStartPage() {
        if (map.isEmpty() || map.get(StartPage.NAMEPAGE) == null) {
            map.put(StartPage.NAMEPAGE, new StartPage());
        }
        return (StartPage) map.get(StartPage.NAMEPAGE);
    }

    public SearchResultsPage getSearchResultsPage() {
        if (map.isEmpty() || map.get(SearchResultsPage.NAMEPAGE) == null) {
            map.put(SearchResultsPage.NAMEPAGE, new SearchResultsPage());
        }
        return (SearchResultsPage) map.get(SearchResultsPage.NAMEPAGE);
    }

    public CartPages getCartPages() {
        if (map.isEmpty() || map.get(CartPages.NAMEPAGE) == null) {
            map.put(CartPages.NAMEPAGE, new CartPages());
        }
        return (CartPages) map.get(CartPages.NAMEPAGE);
    }
}