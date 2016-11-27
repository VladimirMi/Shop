package ru.mikhalev.vladimir.mvpauth.catalog;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.CatalogScope;
import ru.mikhalev.vladimir.mvpauth.core.layers.presenter.AbstractPresenter;
import ru.mikhalev.vladimir.mvpauth.home.HomeActivity;
import ru.mikhalev.vladimir.mvpauth.home.IRootView;
import ru.mikhalev.vladimir.mvpauth.home.RootPresenter;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */
public class CatalogPresenter extends AbstractPresenter<ICatalogView> implements ICatalogPressenter {
    @Inject
    CatalogModel mCatalogModel;

    @Inject
    RootPresenter mRootPresenter;

    private List<ProductDto> mProductList;
    private int mCartCounter = 0;

    public CatalogPresenter() {
        DaggerService.getComponent(CatalogPresenter.Component.class,
                DaggerService.getComponent(HomeActivity.Component.class),
                new CatalogPresenter.Module()).inject(this);
    }

    @Override
    public void initView() {
        if (mProductList == null) {
            mProductList = mCatalogModel.getProductList();
        }

        if (getView() != null) {
            getView().showCatalogView(mProductList);
            getView().updateProductCounter(mCartCounter);
        }
    }

    @Override
    public void clickOnBuyButton(int position) {
        if (getView() != null) {
            if (checkUserAuth()) {
                getRootView().showMessage("Товар " + mProductList.get(position).getProductName()
                        + " успешно добавлен в корзину");
                mCartCounter += mProductList.get(position).getCount();
                getView().updateProductCounter(mCartCounter);
            } else {
                getView().showAuthScreen();
            }
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mCatalogModel.isUserAuth();
    }

    private IRootView getRootView() {
        return mRootPresenter.getView();
    }

    //region ==================== DI ========================

    @dagger.Module
    public static class Module {
        @Provides
        @CatalogScope
        CatalogModel provideCatalogModel() {
            return new CatalogModel();
        }
    }

    @dagger.Component(dependencies = HomeActivity.Component.class,
            modules = Module.class)
    @CatalogScope
    interface Component {
        void inject(CatalogPresenter presenter);
    }

    //endregion
}
