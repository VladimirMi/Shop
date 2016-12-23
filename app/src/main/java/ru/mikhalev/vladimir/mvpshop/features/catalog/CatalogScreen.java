package ru.mikhalev.vladimir.mvpshop.features.catalog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.Provides;
import io.realm.RealmResults;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.SubscribePresenter;
import ru.mikhalev.vladimir.mvpshop.data.storage.Product;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.CatalogScope;
import ru.mikhalev.vladimir.mvpshop.features.root.IRootView;
import ru.mikhalev.vladimir.mvpshop.features.root.RootActivity;
import ru.mikhalev.vladimir.mvpshop.features.root.RootPresenter;
import ru.mikhalev.vladimir.mvpshop.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
@Screen(R.layout.screen_catalog)
public class CatalogScreen extends BaseScreen<RootActivity.Component> {
    @Override
    public Object createScreenComponent(RootActivity.Component parentComponent) {
        return DaggerCatalogScreen_Component.builder()
                .component(parentComponent)
                .module(new CatalogScreen.Module())
                .build();
    }

    //region =============== DI ==============
    @dagger.Module
    public class Module {
        @Provides
        @CatalogScope
        CatalogScreen.CatalogPresenter provideCatalogPresenter() {
            return new CatalogScreen.CatalogPresenter();
        }

        @Provides
        @CatalogScope
        CatalogModel provideCatalogModel() {
            return new CatalogModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = CatalogScreen.Module.class)
    @CatalogScope
    public interface Component {
        void inject(CatalogScreen.CatalogPresenter presenter);

        void inject(CatalogView view);

        CatalogModel getCatalogModel();
    }

    //endregion

    class CatalogPresenter extends SubscribePresenter<CatalogView> implements ICatalogPressenter {

        @Inject
        CatalogModel mCatalogModel;
        @Inject
        RootPresenter mRootPresenter;
        private int mCartCounter;

        @Nullable
        @Override
        protected IRootView getRootView() {
            return mRootPresenter.getView();
        }

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            scope.<CatalogScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            getView().initView();
            subscribeOnProductList();
            if (getRootView() != null) {
                getRootView().setBasketCounter(mCartCounter);
            }
        }

        @Override
        public void dropView(CatalogView view) {
            super.dropView(view);
            mSubscriptions.unsubscribe();
        }

        private void subscribeOnProductList() {
            mSubscriptions.add(mCatalogModel.getProductsObs()
                    .subscribe(new ViewSubscriber<RealmResults<Product>>() {
                        @Override
                        public void onNext(RealmResults<Product> products) {
                            getView().getAdapter().updateData(products);
                        }
                    }));
        }

        @Override
        public void clickOnBuyButton(int position) {
            if (getView() != null) {
//                mCatalogModel.getProductsObs()
//                        .filter(product -> product.getId() == position)
//                        .subscribe(product -> {
//                            if (checkUserAuth() && getRootView() != null) {
////                                getRootView().showMessage("Товар " + mCatalogModel.getProductFromPosition(position).getProductName()
////                                        + " успешно добавлен в корзину");
////                                mCartCounter += mCatalogModel.getProductFromPosition(position);
//                                getRootView().setBasketCounter(mCartCounter);
//                            } else {
//                                Flow.get(getView()).set(new AuthScreen());
//                            }
//                        });
            }
        }

        @Override
        public boolean checkUserAuth() {
            return mCatalogModel.isUserAuth();
        }
    }

    public static class Factory {
        public static Context createProductContext(Product product, Context parentContext) {
            MortarScope parentScope = MortarScope.getScope(parentContext);
            MortarScope childScope = null;
            ProductCardScreen productCardScreen = new ProductCardScreen(product);
            String scopeName = String.format("%s_%d", productCardScreen.getScopeName(), product.getId());

            if (parentScope.findChild(scopeName) == null) {
                childScope = parentScope.buildChild()
                        .withService(DaggerService.SERVICE_NAME,
                                productCardScreen.createScreenComponent(DaggerService.getDaggerComponent(parentContext)))
                        .build(scopeName);
            } else {
                childScope = parentScope.findChild(scopeName);
            }
            return childScope.createContext(parentContext);
        }
    }
}
