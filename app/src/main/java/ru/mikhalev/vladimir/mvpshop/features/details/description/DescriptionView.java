package ru.mikhalev.vladimir.mvpshop.features.details.description;

import android.content.Context;
import android.util.AttributeSet;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenDescriptionBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.IProductActions;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductViewModel;

/**
 * Developer Vladimir Mikhalev, 14.01.2017.
 */

public class DescriptionView extends BaseView<DescriptionScreen.DescriptionPresenter> implements IProductActions, IDescriptionView {
    private ProductViewModel mViewModel;
    private ScreenDescriptionBinding mBinding;

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


    @Override
    public void setViewModel(ProductViewModel viewModel) {
        mViewModel = viewModel;
        mBinding.setViewModel(mViewModel);
    }


    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public ProductViewModel getViewModel() {
        return mViewModel;
    }

    //region =============== Events ==============

    @Override
    public void clickOnPlus() {
        mViewModel.addProduct();
    }

    @Override
    public void clickOnMinus() {
        mViewModel.deleteProduct();
    }

    @Override
    public void clickOnShowMore() {
        // do nothing
    }

    @Override
    public void clickOnFavorite(boolean checked) {
        mViewModel.setFavorite();
    }

    //endregion
}
