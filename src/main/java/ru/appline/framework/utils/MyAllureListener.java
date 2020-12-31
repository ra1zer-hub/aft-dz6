package ru.appline.framework.utils;

import io.qameta.allure.Attachment;
import io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm;
import ru.appline.framework.entities.ListProducts;

public class MyAllureListener extends AllureCucumber5Jvm {

    @Attachment(value = "Добавленные товары", type = "text/plain", fileExtension = ".txt")
    public static String attachTextFile() {
        return ListProducts.conclusion();
    }
}