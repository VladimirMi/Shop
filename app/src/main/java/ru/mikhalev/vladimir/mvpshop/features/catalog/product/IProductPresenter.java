package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */
public interface IProductPresenter {
    void saveProduct(ProductRealm productRealm);
}
