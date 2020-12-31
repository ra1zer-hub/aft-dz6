package ru.appline.framework.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import ru.appline.framework.managers.PagesManager;

import static ru.appline.framework.entities.ListProducts.getProducts;
import static ru.appline.framework.utils.MyAllureListener.attachTextFile;

public class Steps {

    private PagesManager app = PagesManager.getManagerPages();

    @Когда("^Загружена стартовая страница$")
    public void getInitialPage() {
        app.getStartPage();
    }

    @Когда("^Закрываем появившееся оповещение$")
    public void closeNotification() {
        app.getStartPage().closeNotification();
    }

    @Тогда("^Вводим в поиск '(.*)'$")
    public void search(String search) {
        app.getStartPage().searchByName(search);
    }

    @Когда("^Выбираем фильтр по форме поле/значение:$")
    public void putFilters(DataTable dataTable) {
        dataTable.cells().forEach(
                raw -> app.getSearchResultsPage().addFilter(raw.get(0), raw.get(1))
        );
    }

    @Когда("^Добавляем '([0-9]+|все)' чётн(?:ые|ых|ый) това(?:р|ра|ры|ров) в корзину$")
    public void addProductToCart(String value) {
        if (value.equals("все")) {
            app.getSearchResultsPage().addToCart(Integer.MAX_VALUE);
        } else {
            app.getSearchResultsPage().addToCart(Integer.parseInt(value));
        }
    }

    @Тогда("^Переходим в корзину$")
    public void toCart() {
        app.getSearchResultsPage().toCart();
    }

    @Когда("^Проверяем товары в корзине$")
    public void checkItemsInCart() {
        app.getCartPages().checkingTheItemsInTheCart();
    }

    @Тогда("^Прикрепляем информацию о товарах$")
    public void addInfo() {
        Allure.attachment("Добавленные товары", attachTextFile());
    }

    @Когда("^Проверяем, что отображается текст 'Ваша корзина (\\d+|N) това(?:р|ра|ров)'$")
    public void checkItemsInCart(String numberOfGoods) {
        if (numberOfGoods.equals("N")) {
            app.getCartPages().checkFinalInfo(getProducts().size());
        } else {
            app.getCartPages().checkFinalInfo(Integer.parseInt(numberOfGoods));
        }
    }

    @Тогда("^Удаляем все товары в корзине$")
    public void deleteAllItemsInCart() {
        app.getCartPages().clearCart();
    }
}