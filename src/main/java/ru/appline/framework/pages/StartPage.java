package ru.appline.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StartPage extends BasePage {

    public static final String NAMEPAGE = "StartPage";

    @FindBy(xpath = "//input[@name='search']")
    private WebElement search;

    @FindBy(xpath = "//div[@class]/strong")
    private WebElement nextPage;

    @FindBy(xpath = "//h2[text()='Вы находитесь в зоне очень быстрой доставки!']")
    private WebElement notification;

    public StartPage closeNotification() {
        wait.until(ExpectedConditions.visibilityOf(notification));
        notification = notification.findElement(By.xpath("./../..//button"));
        elementToBeClickable(notification).click();
        wait.until(ExpectedConditions.invisibilityOf(notification));
        return app.getStartPage();
    }

    public SearchResultsPage searchByName(String name) {
        fillSearchField(search, name);
        checkOpenPage(nextPage, name);
        return app.getSearchResultsPage();
    }
}