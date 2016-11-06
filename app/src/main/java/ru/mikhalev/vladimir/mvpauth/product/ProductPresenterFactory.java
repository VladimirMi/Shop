package ru.mikhalev.vladimir.mvpauth.product;

import java.util.HashMap;
import java.util.Map;

/**
 * Developer Vladimir Mikhalev 28.10.2016
 */

public class ProductPresenterFactory {
    public static final Map<String, ProductPresenter> sPresenterMap = new HashMap<>();

    public static void registerPresenter(ProductDto product, ProductPresenter presenter) {
        sPresenterMap.put(String.valueOf(product.getId()), presenter);
    }

    public static  ProductPresenter getInstance(ProductDto product) {
        ProductPresenter presenter = sPresenterMap.get(String.valueOf(product.getId()));
        if (presenter == null) {
            presenter = ProductPresenter.newInstance(product);
            registerPresenter(product, presenter);
        }
        return presenter;
    }
}
