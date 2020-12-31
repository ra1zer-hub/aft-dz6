package ru.appline.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static ru.appline.framework.entities.ListProducts.getProducts;

public class SearchResultsPage extends BasePage {

    public static final String NAMEPAGE = "SearchResultsPage";

    @FindBy(xpath = "//aside/div[contains(@class, 'filter-block')]")
    private List<WebElement> filters;

    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']")
    private WebElement searchResultsFiltersActive;

    @FindBy(xpath = "//div[@data-widget='searchResultsV2']/div/div[@style]")
    private List<WebElement> searchingResults;

    @FindBy(xpath = "//p[normalize-space(text())='Перейти на страницу']/../..//a")
    private List<WebElement> numberOfPages;

    @FindBy(xpath = "//a[@data-widget='cart']")
    private WebElement cart;

    @FindBy(xpath = "//div[normalize-space(text())='Корзина']")
    private WebElement cartPage;

    @FindBy(xpath = "//a[@data-widget='cart']/span[contains(@class, 'f-caption--bold')]")
    private WebElement quantityOfItemsInTheCart;

    @FindBy(xpath = "//div[normalize-space(text())='Корзина']")
    private WebElement nextPage;

    public SearchResultsPage addFilter(String name, String value) {
        for (WebElement option : filters) {
            WebElement optionName = option.findElement(By.xpath("./div"));
            if (optionName.getText().trim().equalsIgnoreCase(name)) {
                switch (name) {
                    case "Цена":
                        filterAtMaxPrice(option, value);
                        break;
                    case "Беспроводные интерфейсы":
                        filterOnWirelessInterfaces(option, value);
                        break;
                    case "Высокий рейтинг":
                        filterByHighRating(option, value);
                        update(searchResultsFiltersActive, name);
                        return app.getSearchResultsPage();
                    case "Бренды":
                        filterByBrand(option, value);
                        break;
                    default:
                        fail("Метод для фильтра '" + name + "' не найден.");
                }
                update(searchResultsFiltersActive, value);
                return app.getSearchResultsPage();
            }
        }
        fail("Фильтр '" + name + "' не найден.");
        return app.getSearchResultsPage();
    }

    public SearchResultsPage addToCart(Integer quantity) {
        int pageCount = (numberOfPages.size() == 0) ? 1 : numberOfPages.size();
        for (int i = 1; i <= pageCount; i++) {
            int count = 1;
            for (WebElement product : searchingResults) {
                if (count % 2 == 0) {
                    WebElement buttonText = product.findElement(By.xpath(".//button//div[text()]"));
                    if (buttonText.getText().equalsIgnoreCase("в корзину")) {
                        addProduct(product, quantityOfItemsInTheCart);
                    }
                    if (getProducts().size() == quantity) {
                        return app.getSearchResultsPage();
                    }
                }
                if (count == searchingResults.size() && pageCount > i) {
                    numberOfPages.get(i).click();
                    refreshNextPageOfResults(numberOfPages.get(i));
                }
                count++;
            }
        }
        return app.getSearchResultsPage();
    }

    public CartPages toCart() {
        elementToBeClickable(cart).click();
        wait.until(ExpectedConditions.visibilityOf(nextPage));
        return app.getCartPages();
    }
}