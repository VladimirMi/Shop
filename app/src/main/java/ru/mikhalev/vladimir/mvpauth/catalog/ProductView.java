package ru.mikhalev.vladimir.mvpauth.catalog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.core.base.BaseViewModel;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.databinding.ScreenProductBinding;


/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
public class ProductView extends LinearLayout implements IProductView, IProductActions {
    private static final String TAG = "ProductView";
    private ScreenProductBinding mBinding;
    @Inject ProductScreen.ProductPresenter mPresenter;

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<ProductScreen.Component>getDaggerComponent(context).inject(this);
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mBinding = ScreenProductBinding.bind(this);
            mBinding.setActionsHandler(this);
        }
    }

    @Override
    public void onAttachedToWindow() {
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
    public void clickOnPlus() {
        mPresenter.clickOnPlus();
    }

    @Override
    public void clickOnMinus() {
        mPresenter.clickOnMinus();
    }

    //endregion

    //region ==================== IProductView ========================


    @Override
    public void setViewModel(BaseViewModel viewModel) {
        mBinding.setViewModel((ProductViewModel) viewModel);
    }

    @Override
    public void showProductView(ProductViewModel productViewModel) {
        mBinding.setViewModel(productViewModel);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
