package ru.mikhalev.vladimir.mvpauth.catalog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.auth.AuthScreen;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.CatalogScope;
import ru.mikhalev.vladimir.mvpauth.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;
import ru.mikhalev.vladimir.mvpauth.root.IRootView;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;
import ru.mikhalev.vladimir.mvpauth.root.RootPresenter;
import rx.subscriptions.CompositeSubscription;

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

    class CatalogPresenter extends ViewPresenter<CatalogView> implements ICatalogPressenter {

        @Inject
        CatalogModel mCatalogModel;
        @Inject
        RootPresenter mRootPresenter;
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        private int mCartCounter;

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
            mSubscriptions.add(mCatalogModel.getProductsObs().
                    subscribe(getView().getAdapter()::addItem));
        }

        @Nullable
        private IRootView getRootView() {
            return mRootPresenter.getView();
        }

        @Override
        public void clickOnBuyButton(int position) {
            if (getView() != null) {
                mCatalogModel.getProductsObs()
                        .filter(product -> product.getId() == position)
                        .subscribe(product -> {
                            if (checkUserAuth() && getRootView() != null) {
//                                getRootView().showMessage("Товар " + mCatalogModel.getProductFromPosition(position).getProductName()
//                                        + " успешно добавлен в корзину");
//                                mCartCounter += mCatalogModel.getProductFromPosition(position);
                                getRootView().setBasketCounter(mCartCounter);
                            } else {
                                Flow.get(getView()).set(new AuthScreen());
                            }
                        });
            }
        }

        @Override
        public boolean checkUserAuth() {
            return mCatalogModel.isUserAuth();
        }
    }

    public static class Factory {
        public static Context createProductContext(ProductDto productDto, Context parentContext) {
            MortarScope parentScope = MortarScope.getScope(parentContext);
            MortarScope childScope = null;
            ProductScreen productScreen = new ProductScreen(productDto);
            String scopeName = String.format("%s_%d", productScreen.getScopeName(), productDto.getId());

            if (parentScope.findChild(scopeName) == null) {
                childScope = parentScope.buildChild()
                        .withService(DaggerService.SERVICE_NAME,
                                productScreen.createScreenComponent(DaggerService.getDaggerComponent(parentContext)))
                        .build(scopeName);
            } else {
                childScope = parentScope.findChild(scopeName);
            }
            return childScope.createContext(parentContext);
        }
    }
}
