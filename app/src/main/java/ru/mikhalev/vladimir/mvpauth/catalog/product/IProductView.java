package ru.mikhalev.vladimir.mvpauth.catalog.product;

import ru.mikhalev.vladimir.mvpauth.core.base.view.IView;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */

public interface IProductView extends IView{
    void showProductView(ProductDto product);
    void updateProductCountView(ProductDto product);
}
