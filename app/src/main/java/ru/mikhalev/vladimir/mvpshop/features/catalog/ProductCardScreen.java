package ru.mikhalev.vladimir.mvpshop.features.catalog;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.Provides;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.data.storage.Product;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.ProductScope;
import ru.mikhalev.vladimir.mvpshop.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_product_card)
public class ProductCardScreen extends BaseScreen<CatalogScreen.Component> {
    private ProductCardViewModel mViewModel;

    public ProductCardScreen(Product product) {
        mViewModel = new ProductCardViewModel(product);
    }

    public ProductCardViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ProductCardScreen && mViewModel.equals(((ProductCardScreen) o).mViewModel);
    }

    @Override
    public int hashCode() {
        return mViewModel.hashCode();
    }

    @Override
    public Object createScreenComponent(CatalogScreen.Component parentComponent) {
        return DaggerProductCardScreen_Component.builder()
                .component(parentComponent)
                .module(new ProductCardScreen.Module())
                .build();
    }

    //region ==================== DI ========================

    @dagger.Module
    public class Module {

        @Provides
        @ProductScope
        ProductCardPresenter provideProductPresenter() {
            return new ProductCardPresenter();
        }
    }

    @dagger.Component(dependencies = CatalogScreen.Component.class, modules = Module.class)
    @ProductScope
    public interface Component {
        void inject(ProductCardPresenter presenter);

        void inject(ProductCardView view);
    }

    //endregion

    class ProductCardPresenter extends ViewPresenter<ProductCardView> implements IProductCardPresenter {

        @Inject CatalogModel mCatalogModel;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            scope.<ProductCardScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            getView().setViewModel(mViewModel);
            getView().initView();
        }

        //region ==================== IProductCardPresenter ========================

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
            // TODO: 17.12.2016 flow show detail screen
        }

        @Override
        public void clickOnFavorite(boolean checked) {

        }

        //endregion
    }
}
