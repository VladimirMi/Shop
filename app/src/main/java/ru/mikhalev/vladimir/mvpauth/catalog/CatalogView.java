package ru.mikhalev.vladimir.mvpauth.catalog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.databinding.ScreenCatalogBinding;
import ru.mikhalev.vladimir.mvpauth.di.DaggerService;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
public class CatalogView extends RelativeLayout implements ICatalogView, ICatalogActions {

    @Inject CatalogScreen.CatalogPresenter mPresenter;
    private ScreenCatalogBinding mBinding;
    private CatalogAdapter mAdapter;

    public CatalogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<CatalogScreen.Component>getDaggerComponent(context).inject(this);
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mBinding = ScreenCatalogBinding.bind(this);
            mBinding.setActionsHandler(this);
            mBinding.pagerIndicator.setViewPager(mBinding.productPager);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }
    //endregion

    //region ==================== Events ========================

    @Override
    public void clickOnAddGoodsToBasket() {
        mPresenter.clickOnBuyButton(mBinding.productPager.getCurrentItem());
    }

    //endregion

    //region ==================== ICatalogView ========================

    public void initView() {
        mAdapter = new CatalogAdapter();
        mBinding.productPager.setAdapter(mAdapter);
    }

    public CatalogAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void showCatalogView(List<ProductViewModel> productList) {

    }

    @Override
    public void updateProductCounter(int i) {
//        getRootActivity().setBasketCounter(count);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
