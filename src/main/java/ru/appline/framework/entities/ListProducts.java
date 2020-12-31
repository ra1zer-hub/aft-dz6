package ru.appline.framework.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListProducts {
    private static List<Product> products = new ArrayList<>();

    public static List<Product> getProducts() {
        return products;
    }

    public static String conclusion() {
        return String.format("%-100s%-15s%n", "Наименование", "Цена")
                + ("---------------------------------------------------------")
                + ("---------------------------------------------------------\n")
                + products.stream()
                .map(String::valueOf)
                .collect(Collectors.joining())
                + "\nТовар с наибольшей ценой: " + products.stream()
                .max(Comparator.comparing(Product::getPrice))
                .get().getName();
    }

    public static void clearProducts() {
        products.clear();
    }
}
