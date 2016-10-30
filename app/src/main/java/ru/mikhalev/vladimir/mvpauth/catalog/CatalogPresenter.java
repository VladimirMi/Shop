package ru.mikhalev.vladimir.mvpauth.catalog;

import java.util.List;

import ru.mikhalev.vladimir.mvpauth.core.base.presenter.AbstractPresenter;
import ru.mikhalev.vladimir.mvpauth.catalog.product.ProductDto;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */
public class CatalogPresenter extends AbstractPresenter<ICatalogView> implements ICatalogPressenter {
    private static CatalogPresenter ourInstance = new CatalogPresenter();
    private CatalogModel mCatalogModel;
    private List<ProductDto> mProductList;
    private int mCartCounter = 0;

    private CatalogPresenter() {
        mCatalogModel = new CatalogModel();
    }

    public static CatalogPresenter getInstance() {
        return ourInstance;
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
                getView().showAddToCartMessage(mProductList.get(position));
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
}
