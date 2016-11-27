package ru.mikhalev.vladimir.mvpauth.catalog;

import javax.inject.Inject;

import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.ProductScope;
import ru.mikhalev.vladimir.mvpauth.core.layers.presenter.AbstractPresenter;

/**
 * Developer Vladimir Mikhalev 28.10.2016
 */
public class ProductPresenter extends AbstractPresenter<IProductView> implements IProductPresenter{
    @Inject
    ProductModel mProductModel;
    private ProductDto mProduct;

    public static ProductPresenter newInstance(ProductDto product) {
        return new ProductPresenter(product);
    }

    public ProductPresenter(ProductDto product) {
        DaggerService.getComponent(Component.class).inject(this);
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

    //region ==================== DI ========================

    @dagger.Module
    public static class Module {
        @Provides
        @ProductScope
        ProductModel provideProductModel() {
            return new ProductModel();
        }
    }

    @dagger.Component(modules = Module.class)
    @ProductScope
    interface Component {
        void inject(ProductPresenter presenter);
    }

    //endregion
}
