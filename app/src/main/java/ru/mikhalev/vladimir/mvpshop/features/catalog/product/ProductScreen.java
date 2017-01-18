package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.os.Bundle;

import dagger.Provides;
import flow.Flow;
import io.realm.Realm;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogModel;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpshop.features.details.DetailsScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_product)
public class ProductScreen extends BaseScreen<CatalogScreen.Component> {
    private ProductViewModel mViewModel;
    private ProductRealm mProductRealm;

    public ProductScreen(ProductRealm productRealm) {
        mViewModel = new ProductViewModel(productRealm);
        mProductRealm = productRealm;
    }

    public ProductViewModel getViewModel() {
        return mViewModel;
    }

//    @Override
//    public boolean equals(Object o) {
//        return o instanceof ProductScreen && mViewModel.equals(((ProductScreen) o).mViewModel);
//    }
//
//    @Override
//    public int hashCode() {
//        return mViewModel.hashCode();
//    }

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
        @DaggerScope(ProductScreen.class)
        ProductPresenter provideProductPresenter() {
            return new ProductPresenter(mProductRealm);
        }
    }

    @dagger.Component(dependencies = CatalogScreen.Component.class, modules = Module.class)
    @DaggerScope(ProductScreen.class)
    public interface Component {
        void inject(ProductPresenter presenter);

        void inject(ProductView view);
    }

    //endregion

    public class ProductPresenter extends BasePresenter<ProductView, CatalogModel> implements IProductPresenter {

        private final ProductRealm mProduct;

        public ProductPresenter(ProductRealm productRealm) {
            Realm realm = Realm.getDefaultInstance();
            mProduct = realm.copyFromRealm(productRealm);
            realm.close();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            scope.<ProductScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            // nothing
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            getView().setViewModel(mViewModel);
        }

        //region ==================== IProductPresenter ========================

        @Override
        public void clickOnPlus() {
            mProduct.inc();
            mModel.updateProduct(mProduct);
        }

        @Override
        public void clickOnMinus() {
            mProduct.dec();
            mModel.updateProduct(mProduct);
        }

        @Override
        public void clickOnShowMore() {
            Flow.get(getView()).set(new DetailsScreen(mProductRealm));
        }

        @Override
        public void clickOnFavorite() {
            mProduct.switchFavorite();
            mModel.updateProduct(mProduct);
        }

        //endregion
    }
}
