package ru.appline.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.entities.Product;
import ru.appline.framework.managers.PagesManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static ru.appline.framework.entities.ListProducts.getProducts;
import static ru.appline.framework.managers.DriverManager.getDriver;

public class BasePage {

    protected WebDriverWait wait = new WebDriverWait(getDriver(), 20, 1000);
    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();
    protected PagesManager app = PagesManager.getManagerPages();

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    protected WebElement elementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void fillSearchField(WebElement field, String value) {
        elementToBeClickable(field).click();
        field.sendKeys(value);
        assertEquals(value, field.getAttribute("value"), "Поле поиска было заполнено некорректно");
        field.sendKeys(Keys.ENTER);
    }

    protected void checkOpenPage(WebElement pageElement, String request) {
        wait.until(ExpectedConditions.visibilityOf(pageElement));
        assertEquals(request, pageElement.getText(), "Открылась страница не соответствующая запросу");
    }

    protected void filterAtMaxPrice(WebElement field, String value) {
        field = field.findElement(By.xpath(".//input[@qa-id='range-to']"));
        elementToBeClickable(field).click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
        assertEquals(value, field.getAttribute("value"), "Поле заполнено не правильно.");
        field.sendKeys(Keys.ENTER);
    }

    protected void update(WebElement searchResultsFiltersActive, String value) {
        int count = 0;
        String regex = "(?U)(?<=[0-9])[\\W_]+(?=[0-9])";
        while (!searchResultsFiltersActive.getText().replaceAll(regex, "").contains(value) && count < 25) {
            sleep(200);
            count++;
        }
    }

    protected void refreshNextPageOfResults(WebElement page) {
        int count = 0;
        while (page.getAttribute("className").length() < 6 && count < 25) {
            sleep(200);
            count++;
        }
    }

    protected void filterByHighRating(WebElement checkbox, String value) {
        checkbox = checkbox.findElement(By.xpath(".//input"));
        WebElement checkboxButton = checkbox.findElement(By.xpath("./.."));
        if (value.equals("true") && !checkbox.isSelected()) {
            elementToBeClickable(checkboxButton).click();
        } else if (value.equals("false") && checkbox.isSelected()) {
            elementToBeClickable(checkboxButton).click();
        }
    }

    protected void filterOnWirelessInterfaces(WebElement checkbox, String value) {
        List<WebElement> checkboxes = checkbox.findElements(By.xpath(".//label"));
        for (WebElement element : checkboxes) {
            if (element.getText().equalsIgnoreCase(value)) {
                if (!element.isSelected()) {
                    elementToBeClickable(element.findElement(By.xpath("./.."))).click();
                }
            }
        }
    }

    protected void filterByBrand(WebElement option, String value) {
        WebElement seeAll = option.findElement(By.xpath(".//span[@class='show']"));
        if (seeAll.getText().equalsIgnoreCase("Посмотреть все")) {
            elementToBeClickable(seeAll).click();
        }
        WebElement input = option.findElement(By.xpath(".//input[not (@type='checkbox')]"));
        elementToBeClickable(input).click();
        input.sendKeys(value);
        List<WebElement> results = option.findElements(By.xpath(".//span[not (text()='Свернуть') and not(@class='show')]"));
        for (WebElement result : results) {
            WebElement checkCheckbox = result.findElement(By.xpath("./../.."));
            elementToBeClickable(checkCheckbox).click();
        }
    }

    protected void addProduct(WebElement product, WebElement quantityOfItemsInTheCart) {
        int quantity = Integer.parseInt(quantityOfItemsInTheCart.getText());
        String productName = product.findElement(By.xpath(".//div[contains(@style, 'width: 50%')]//a[contains(@class, 'target')]"))
                .getText();
        double productPrice = getNumberFromWebElement(product.findElements(By.xpath(".//span[contains(text(), '₽')]"))
                .get(0));

        WebElement addToCartButton = product.findElement(By.xpath(".//button//div[text()]"));
        WebElement blockWithPic = product.findElement(By.xpath(".//a[@class='a0v2 a0v4 tile-hover-target']"));

        if (!blockWithPic.getText().contains("Express") && !blockWithPic.getText().contains("Ozon Global")) {
            elementToBeClickable(addToCartButton).click();
            getProducts().add(new Product(productName, productPrice));
            int count = 0;
            while (Integer.parseInt(quantityOfItemsInTheCart.getText()) == (quantity++) && count < 25) {
                sleep(200);
                count++;
            }
        }
    }

    protected double getNumberFromWebElement(WebElement element) {
        return Double.parseDouble(element.getText().replaceAll("[\\D.]", ""));
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}