package ru.mikhalev.vladimir.mvpshop.features.catalog;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenCatalogBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
public class CatalogView extends RelativeLayout implements ICatalogView, ICatalogActions {

    @Inject CatalogScreen.CatalogPresenter mPresenter;
    private ScreenCatalogBinding mBinding;
    private CatalogAdapter mAdapter;
    private boolean isExitEnabled = false;

    public CatalogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Timber.d("CatalogView:");
        if (!isInEditMode()) {
            DaggerService.<CatalogScreen.Component>getDaggerComponent(context).inject(this);
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Timber.d("onFinishInflate:");
        if (!isInEditMode()) {
            mBinding = ScreenCatalogBinding.bind(this);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Timber.d("onAttachedToWindow:");
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Timber.d("onDetachedFromWindow:");
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


    @Override
    public void setViewModel(BaseViewModel viewModel) {
        mBinding.setActionsHandler(this);
    }

    @Override
    public void initView() {
        mAdapter = new CatalogAdapter();
        mBinding.productPager.setAdapter(mAdapter);
        mBinding.pagerIndicator.setupWithViewPager(mBinding.productPager);
    }

    public CatalogAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void showCatalogView(List<ProductCardViewModel> productList) {
    }

    @Override
    public void updateProductCounter(int i) {
//        getRootActivity().setBasketCounter(count);
    }

    @Override
    public boolean viewOnBackPressed() {
        if (isExitEnabled) {
            return false;
        } else {
            isExitEnabled = true;
            new Handler().postDelayed(() -> isExitEnabled = false, 2000);
            mPresenter.getRootView().showMessage(getRootView().getContext().getString(R.string.message_exit));
            return true;
        }
    }

    //endregion
}
