package ru.mikhalev.vladimir.mvpshop.features.details;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenDetailsBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.IProductActions;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductScreen;

/**
 * Developer Vladimir Mikhalev 23.12.2016
 */

public class DetailsView extends BaseView<ProductScreen.ProductPresenter> implements IProductActions {

    private ScreenDetailsBinding mBinding;

    public DetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<DetailsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initBinding() {
        mBinding = ScreenDetailsBinding.bind(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void setViewModel(BaseViewModel viewModel) {
        DetailsAdapter adapter = new DetailsAdapter();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public ViewPager getViewPager() {
        return mBinding.pager;
    }

    //region =============== Events ==============

    @Override
    public void clickOnPlus() {
        // TODO: 06.01.2017 to VM
    }

    @Override
    public void clickOnMinus() {
        // TODO: 06.01.2017 to VM
    }

    @Override
    public void clickOnShowMore() {
        // do nothing
    }

    @Override
    public void clickOnFavorite(boolean checked) {
        // TODO: 06.01.2017 to presenter
    }

    //endregion
}
