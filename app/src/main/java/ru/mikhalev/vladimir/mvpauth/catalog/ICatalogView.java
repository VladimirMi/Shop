package ru.mikhalev.vladimir.mvpauth.catalog;

import java.util.List;

import ru.mikhalev.vladimir.mvpauth.core.layers.view.IView;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public interface ICatalogView extends IView {
    void showCatalogView(List<ProductViewModel> productList);

    void updateProductCounter(int i);
}
