package ru.mikhalev.vladimir.mvpauth.catalog;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.Provides;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.ProductScope;
import ru.mikhalev.vladimir.mvpauth.flow.AbsScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_product)
public class ProductScreen extends AbsScreen<CatalogScreen.Component> {
    private ProductViewModel mProductViewModel;

    public ProductScreen(ProductViewModel productViewModel) {
        mProductViewModel = productViewModel;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ProductScreen && mProductViewModel.equals(((ProductScreen) o).mProductViewModel);
    }

    @Override
    public int hashCode() {
        return mProductViewModel.hashCode();
    }

    @Override
    public Object createScreenComponent(CatalogScreen.Component parentComponent) {
        return DaggerProductScreen_Component.builder()
                .component(parentComponent)
                .module(new ProductScreen.Module())
                .build();
    }

    //region ==================== DI ========================

    @dagger.Module
    public class Module {

        @Provides
        @ProductScope
        ProductScreen.ProductPresenter provideProductPresenter() {
            return new ProductScreen.ProductPresenter(mProductViewModel);
        }
    }

    @dagger.Component(dependencies = CatalogScreen.Component.class, modules = Module.class)
    @ProductScope
    interface Component {
        void inject(ProductScreen.ProductPresenter presenter);

        void inject(ProductView view);
    }

    //endregion

    class ProductPresenter extends ViewPresenter<ProductView> implements IProductPresenter {

        @Inject CatalogModel mCatalogModel;

        public ProductPresenter(ProductViewModel product) {

        }

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((ProductScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().showProductView(mProductViewModel);
            }
        }

        //region ==================== IProductPresenter ========================

        @Override
        public void clickOnPlus() {
            mProductViewModel.addProduct();
            mCatalogModel.updateProduct(mProductViewModel);
        }

        @Override
        public void clickOnMinus() {
            if (mProductViewModel.getCount() > 1) {
                mProductViewModel.deleteProduct();
                mCatalogModel.updateProduct(mProductViewModel);
            }
        }

        //endregion
    }
}
