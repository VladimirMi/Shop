package ru.mikhalev.vladimir.mvpauth.catalog;

import java.util.List;

import ru.mikhalev.vladimir.mvpauth.core.managers.DataManager;
import ru.mikhalev.vladimir.mvpauth.catalog.product.ProductDto;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel {
    DataManager mDataManager = DataManager.getInstance();

    public CatalogModel() {
    }

    public List<ProductDto> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAuth() {
        return mDataManager.isAuthUser();
    }
}
