package ru.mikhalev.vladimir.mvpshop.features.details.description;

import android.content.Context;
import android.util.AttributeSet;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenDescriptionBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.IProductView;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductViewModel;

/**
 * Developer Vladimir Mikhalev, 14.01.2017.
 */

public class DescriptionView extends BaseView<DescriptionScreen.DescriptionPresenter> implements IDescriptionActions, IProductView {
    private ScreenDescriptionBinding mBinding;
    private ProductRealm mProductRealm;

    public DescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<DescriptionScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initView() {
        mBinding = ScreenDescriptionBinding.bind(this);
        mBinding.setActionsHandler(this);
    }

    //region =============== Events ==============

    @Override
    public void clickOnPlus() {
        mProductRealm.inc();
        mPresenter.saveProduct(mProductRealm);
    }

    @Override
    public void clickOnMinus() {
        mProductRealm.dec();
        mPresenter.saveProduct(mProductRealm);
    }

    @Override
    public void clickOnFavorite() {
        mProductRealm.switchFavorite();
        mPresenter.saveProduct(mProductRealm);
    }


    @Override
    public void clickOnRating(float rating) {
        mProductRealm.setRating(rating);
        mPresenter.saveProduct(mProductRealm);
    }

    //endregion

    //region ==================== IProductView ========================

    @Override
    public void setProduct(ProductRealm productRealm) {
        mProductRealm = productRealm;
        ProductViewModel viewModel = mBinding.getViewModel();
        if (viewModel == null) {
            viewModel = new ProductViewModel();
            viewModel.update(productRealm);
            mBinding.setViewModel(viewModel);
        } else {
            viewModel.update(productRealm);
        }
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
