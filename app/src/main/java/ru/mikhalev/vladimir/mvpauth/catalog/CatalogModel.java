package ru.mikhalev.vladimir.mvpauth.catalog;

import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.data.dto.Product;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel extends BaseModel {

    public Observable<Product> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAuth() {
        return mDataManager.isAuthUser();
    }

    public void updateProduct(Product product) {
        mDataManager.updateProduct(product);
    }

    public Observable<Product> getProductFromPosition(int position) {
        return mDataManager.getProductFromPosition(position);
    }
}
