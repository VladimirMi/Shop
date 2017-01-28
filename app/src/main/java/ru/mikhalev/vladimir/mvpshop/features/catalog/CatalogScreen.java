package ru.mikhalev.vladimir.mvpshop.features.catalog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.Locale;

import dagger.Provides;
import io.realm.RealmResults;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductScreen;
import ru.mikhalev.vladimir.mvpshop.features.root.IRootView;
import ru.mikhalev.vladimir.mvpshop.features.root.MenuItemHolder;
import ru.mikhalev.vladimir.mvpshop.features.root.RootActivity;
import ru.mikhalev.vladimir.mvpshop.features.root.RootPresenter;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;
import rx.Subscription;

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
        @DaggerScope(CatalogScreen.class)
        CatalogScreen.CatalogPresenter provideCatalogPresenter() {
            return new CatalogScreen.CatalogPresenter();
        }

        @Provides
        @DaggerScope(CatalogScreen.class)
        CatalogModel provideCatalogModel() {
            return new CatalogModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = CatalogScreen.Module.class)
    @DaggerScope(CatalogScreen.class)
    public interface Component {
        void inject(CatalogScreen.CatalogPresenter presenter);

        void inject(CatalogView view);

        CatalogModel getCatalogModel();

        RootPresenter getRootPresenter();
    }

    //endregion

    class CatalogPresenter extends BasePresenter<CatalogView, CatalogModel> implements ICatalogPresenter {

        private int mCartCounter;

        @Override
        protected void initDagger(MortarScope scope) {
            scope.<CatalogScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .addAction(new MenuItemHolder("В корзину", R.drawable.ic_shopping_cart_color_primary_dark_24dp,
                            item -> {
                                getRootView().showMessage("Перейти в корзину");
                                return true;
                            }))
                    .build();
        }


        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            mCompSubs.add(subscribeOnProductList());
            if (getRootView() != null) {
                getRootView().setBasketCounter(mCartCounter);
            }
        }

        @Nullable
        @Override
        protected IRootView getRootView() {
            return super.getRootView();
        }

        private Subscription subscribeOnProductList() {
            return mModel.getProductsObs().subscribe(new ViewSubscriber<RealmResults<ProductRealm>>() {
                @Override
                public void onNext(RealmResults<ProductRealm> productRealms) {
                    getView().getAdapter().updateData(productRealms);
                }
            });
        }


        //region =============== ICatalogPresenter ==============

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
            return mModel.isUserAuth();
        }

        //endregion

    }


    public static class Factory {
        public static Context createProductContext(String productId, Context parentContext) {
            MortarScope parentScope = MortarScope.getScope(parentContext);
            MortarScope childScope = null;
            ProductScreen productScreen = new ProductScreen(productId);
            String scopeName = String.format(Locale.getDefault(), "%s_%s", productScreen.getScopeName(), productId);

            if (parentScope.findChild(scopeName) == null) {
                childScope = parentScope.buildChild()
                        .withService(DaggerService.SERVICE_NAME, productScreen.createScreenComponent(
                                DaggerService.getDaggerComponent(parentContext)))
                        .build(scopeName);
            } else {
                childScope = parentScope.findChild(scopeName);
            }
            return childScope.createContext(parentContext);
        }
    }
}
