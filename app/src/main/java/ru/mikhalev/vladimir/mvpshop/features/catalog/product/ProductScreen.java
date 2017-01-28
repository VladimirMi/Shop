package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.os.Bundle;

import dagger.Provides;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogModel;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;
import rx.Subscription;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_product)
public class ProductScreen extends BaseScreen<CatalogScreen.Component> {

    private final String mProductId;

    public ProductScreen(String id) {
        mProductId = id;
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
            return new ProductPresenter();
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


        @Override
        protected void initDagger(MortarScope scope) {
            scope.<ProductScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            // do nothing
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            mCompSubs.add(subscribeOnProductObs(mProductId));
        }

        private Subscription subscribeOnProductObs(String productId) {
            return mModel.getProductObs(productId)
                    .subscribe(productRealm -> getView().setProduct(productRealm));
        }

        @Override
        public void saveProduct(ProductRealm productRealm) {
            mModel.saveProduct(productRealm);
        }
    }
}
