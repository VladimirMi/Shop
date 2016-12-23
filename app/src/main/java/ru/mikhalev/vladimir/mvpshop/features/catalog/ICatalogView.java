package ru.mikhalev.vladimir.mvpshop.features.catalog;

import java.util.List;

import ru.mikhalev.vladimir.mvpshop.core.IView;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public interface ICatalogView extends IView {
    void showCatalogView(List<ProductCardViewModel> productList);

    void updateProductCounter(int i);
}
