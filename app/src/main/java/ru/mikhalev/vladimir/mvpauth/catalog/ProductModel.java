package ru.mikhalev.vladimir.mvpauth.catalog;

import ru.mikhalev.vladimir.mvpauth.core.managers.DataManager;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */

public class ProductModel {
    private DataManager mDataManager;


    public ProductDto getProductById(int productId) {
        // TODO: 27-Oct-16 get product from manager
        return mDataManager.getProductById(productId);
    }

    public void updateProduct(ProductDto product) {
        mDataManager.updateProduct(product);
    }
}
