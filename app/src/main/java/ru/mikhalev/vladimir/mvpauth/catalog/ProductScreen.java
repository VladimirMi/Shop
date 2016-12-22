package ru.mikhalev.vladimir.mvpauth.catalog;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.Provides;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.data.storage.Product;
import ru.mikhalev.vladimir.mvpauth.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.di.scopes.ProductScope;
import ru.mikhalev.vladimir.mvpauth.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_product)
public class ProductScreen extends BaseScreen<CatalogScreen.Component> {
    private ProductViewModel mViewModel;

    public ProductScreen(Product product) {
        mViewModel = new ProductViewModel(product);
    }

    public ProductViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ProductScreen && mViewModel.equals(((ProductScreen) o).mViewModel);
    }

    @Override
    public int hashCode() {
        return mViewModel.hashCode();
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
            return new ProductScreen.ProductPresenter();
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

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            scope.<ProductScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView();
            }
        }

        //region ==================== IProductPresenter ========================

        @Override
        public void clickOnPlus() {
            mViewModel.addProduct();
            mCatalogModel.updateProduct(new Product(mViewModel));
        }

        @Override
        public void clickOnMinus() {
            if (mViewModel.getCount() > 1) {
                mViewModel.deleteProduct();
                mCatalogModel.updateProduct(new Product(mViewModel));
            }
        }

        @Override
        public void clickOnShowMore() {
            // TODO: 17.12.2016 flow show datail screen
        }

        @Override
        public void clickOnFavorite(boolean checked) {

        }

        //endregion
    }
}
