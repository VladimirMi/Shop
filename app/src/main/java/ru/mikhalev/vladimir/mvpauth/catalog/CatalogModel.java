package ru.mikhalev.vladimir.mvpauth.catalog;

import java.util.List;

import ru.mikhalev.vladimir.mvpauth.core.layers.model.AbstractModel;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel extends AbstractModel{

    public List<ProductDto> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAuth() {
        return mDataManager.isAuthUser();
    }
}
