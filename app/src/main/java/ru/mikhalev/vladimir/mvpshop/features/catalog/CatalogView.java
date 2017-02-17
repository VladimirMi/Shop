package ru.mikhalev.vladimir.mvpshop.features.catalog;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import java.util.List;

import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenCatalogBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductViewModel;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
public class CatalogView extends BaseView<CatalogScreen.CatalogPresenter> implements ICatalogView, ICatalogActions {

    private ScreenCatalogBinding mBinding;
    private CatalogAdapter mAdapter;
    private boolean isExitEnabled = false;

    public CatalogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<CatalogScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initView() {
        mBinding = ScreenCatalogBinding.bind(this);
        mAdapter = new CatalogAdapter();
        mBinding.productPager.setAdapter(mAdapter);
        mBinding.pagerIndicator.setupWithViewPager(mBinding.productPager);
    }


    //region ==================== Events ========================

    @Override
    public void clickOnAddGoodsToBasket() {
        mPresenter.clickOnBuyButton(mBinding.productPager.getCurrentItem());
    }

    //endregion

    //region ==================== ICatalogView ========================


    @Override
    public void setViewModel() {
        mBinding.setActionsHandler(this);
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
        if (isExitEnabled) {
            return false;
        } else {
            isExitEnabled = true;
            new Handler().postDelayed(() -> isExitEnabled = false, 2000);
            mPresenter.getRootView().showMessage(this.getContext().getString(R.string.message_exit));
            return true;
        }
    }

    //endregion
}
