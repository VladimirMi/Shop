package ru.mikhalev.vladimir.mvpshop.features.catalog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import javax.inject.Inject;

import flow.Flow;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenProductCardBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;


/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
public class ProductCardView extends LinearLayout implements IProductCardView, IProductCardActions {
    @Inject ProductCardScreen.ProductCardPresenter mPresenter;
    private ScreenProductCardBinding mBinding;
    private ProductCardViewModel mViewModel;

    public ProductCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<ProductCardScreen.Component>getDaggerComponent(context).inject(this);
            ProductCardScreen screen = Flow.getKey(this);
            if (screen != null) {
                mViewModel = screen.getViewModel();
            }
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mBinding = ScreenProductCardBinding.bind(this);
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

    @Override
    public void clickOnShowMore() {
        mPresenter.clickOnShowMore();
    }

    @Override
    public void clickOnFavorite(boolean checked) {
        mPresenter.clickOnFavorite(checked);
    }

    //endregion

    //region ==================== IProductCardView ========================


    @Override
    public void initView() {
        mBinding.setViewModel(mViewModel);
        mBinding.setActionsHandler(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
