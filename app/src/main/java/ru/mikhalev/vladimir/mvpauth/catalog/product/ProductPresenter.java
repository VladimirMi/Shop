package ru.mikhalev.vladimir.mvpauth.catalog.product;

import ru.mikhalev.vladimir.mvpauth.core.base.presenter.AbstractPresenter;

/**
 * Developer Vladimir Mikhalev 28.10.2016
 */
public class ProductPresenter extends AbstractPresenter<IProductView> implements IProductPresenter{
    private ProductModel mProductModel;
    private ProductDto mProduct;

    public static ProductPresenter newInstance(ProductDto product) {
        return new ProductPresenter(product);
    }

    public ProductPresenter(ProductDto product) {
        mProductModel = new ProductModel();
        mProduct = product;
    }

    @Override
    public void initView() {
        if (getView() != null) {
            getView().showProductView(mProduct);
        }
    }

    @Override
    public void clickOnPlus() {
        mProduct.addProduct();
        mProductModel.updateProduct(mProduct);
        if (getView() != null) {
            getView().updateProductCountView(mProduct);
        }
    }

    @Override
    public void clickOnMinus() {
        if (mProduct.getCount() > 1) {
            mProduct.deleteProduct();
            mProductModel.updateProduct(mProduct);
        }
        if (getView() != null) {
            getView().updateProductCountView(mProduct);
        }
    }
}
