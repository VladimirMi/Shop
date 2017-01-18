package ru.mikhalev.vladimir.mvpshop.features.catalog.product;


import ru.mikhalev.vladimir.mvpshop.core.IView;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */

public interface IProductView extends IView {

    void setProduct(ProductRealm productRealm);

    void setViewModel(ProductViewModel viewModel);
}
