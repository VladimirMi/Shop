package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.content.Context;
import android.util.AttributeSet;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenProductBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;


/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
public class ProductView extends BaseView<ProductScreen.ProductPresenter> implements IProductView, IProductActions {
    private ScreenProductBinding mBinding;
    private ProductViewModel mViewModel;

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {

        }
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<ProductScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initBinding() {
        mBinding = ScreenProductBinding.bind(this);
    }

    //region ==================== Events ========================

    @Override
    public void clickOnPlus() {
        mPresenter.clickOnPlus();
    }

    @Override
    public void clickOnMinus() {
        mPresenter.clickOnMinus();
    }

    @Override
    public void clickOnShowMore() {
        mPresenter.clickOnShowMore();
    }

    @Override
    public void clickOnFavorite(boolean checked) {
        mPresenter.clickOnFavorite(checked);
    }

    //endregion

    //region ==================== IProductView ========================


    @Override
    public void setViewModel(BaseViewModel viewModel) {
        mViewModel = (ProductViewModel) viewModel;
        mBinding.setViewModel(mViewModel);
        mBinding.setActionsHandler(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
