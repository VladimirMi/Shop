package ru.mikhalev.vladimir.mvpshop.features.catalog;

import java.util.List;

import ru.mikhalev.vladimir.mvpshop.core.IView;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductViewModel;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public interface ICatalogView extends IView {
    void setViewModel();

    void showCatalogView(List<ProductViewModel> productList);

    void updateProductCounter(int i);
}
