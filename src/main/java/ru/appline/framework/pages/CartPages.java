package ru.appline.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static ru.appline.framework.entities.ListProducts.getProducts;

public class CartPages extends BasePage {

    public static final String NAMEPAGE = "CartPages";

    @FindBy(xpath = "//div[@data-widget='split']/div[not (contains(@class, 'v-portal')) and position()>2]")
    private List<WebElement> itemsInCart;

    @FindBy(xpath = "//section[@data-widget='total']/div/div/span")
    private List<WebElement> totalInfo;

    @FindBy(xpath = "//span[normalize-space(text())='Удалить выбранные']")
    private WebElement deleteSelected;

    @FindBy(xpath = "//div[@class='modal-content']//button")
    private WebElement deleteButton;

    @FindBy(xpath = "//h1")
    private WebElement title;

    public CartPages checkingTheItemsInTheCart() {
        for (WebElement item : itemsInCart) {
            String itemName = item.findElement(By.xpath("./div/a/span")).getText();
            if (getProducts().stream()
                    .noneMatch(product -> product.getName().equals(itemName))) {
                fail("Не все ранее добавленные товары находятся в корзине");
            }
        }
        return app.getCartPages();
    }

    public CartPages checkFinalInfo(int number) {
        String expected = "Ваша корзина " + number + " товаров";
        String quantityOfItemsInTheCart = totalInfo.get(0).getText() + " " + totalInfo.get(1).getText()
                .split("\\•")[0].trim();
        assertEquals(expected, quantityOfItemsInTheCart, "Актуальный текст не соответствует ожидаемому");
        return app.getCartPages();
    }

    public CartPages clearCart() {
        elementToBeClickable(deleteSelected).click();
        elementToBeClickable(deleteButton).click();
        assertEquals("Корзина пуста", title.getText(), "Корзина не пустая");
        return app.getCartPages();
    }
}