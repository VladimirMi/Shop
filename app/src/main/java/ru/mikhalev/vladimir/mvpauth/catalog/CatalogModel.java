package ru.mikhalev.vladimir.mvpauth.catalog;

import java.util.List;

import ru.mikhalev.vladimir.mvpauth.core.layers.model.AbstractModel;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel extends AbstractModel{

    public List<ProductViewModel> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAuth() {
        return mDataManager.isAuthUser();
    }

    public ProductViewModel getProductById(int productId) {
        // TODO: 27-Oct-16 get product from manager
        return mDataManager.getProductById(productId);
    }

    public void updateProduct(ProductViewModel product) {
        mDataManager.updateProduct(product);
    }
}
