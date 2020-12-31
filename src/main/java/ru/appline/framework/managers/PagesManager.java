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
    private static Map<String, BasePage> basePageMap = new HashMap<>();

    private PagesManager() {
    }

    public static void pagesIsNull() {
        basePageMap.clear();
        clearProducts();
    }

    public static PagesManager getManagerPages() {
        if (pagesManager == null) {
            pagesManager = new PagesManager();
        }
        return pagesManager;
    }

    public StartPage getStartPage() {
        if (basePageMap.isEmpty() || basePageMap.get(StartPage.NAMEPAGE) == null) {
            basePageMap.put(StartPage.NAMEPAGE, new StartPage());
        }
        return (StartPage) basePageMap.get(StartPage.NAMEPAGE);
    }

    public SearchResultsPage getSearchResultsPage() {
        if (basePageMap.isEmpty() || basePageMap.get(SearchResultsPage.NAMEPAGE) == null) {
            basePageMap.put(SearchResultsPage.NAMEPAGE, new SearchResultsPage());
        }
        return (SearchResultsPage) basePageMap.get(SearchResultsPage.NAMEPAGE);
    }

    public CartPages getCartPages() {
        if (basePageMap.isEmpty() || basePageMap.get(CartPages.NAMEPAGE) == null) {
            basePageMap.put(CartPages.NAMEPAGE, new CartPages());
        }
        return (CartPages) basePageMap.get(CartPages.NAMEPAGE);
    }
}