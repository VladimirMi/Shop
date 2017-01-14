package ru.mikhalev.vladimir.mvpshop.features.details;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.data.storage.Product;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenDetailsBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.IProductView;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductViewModel;

/**
 * Developer Vladimir Mikhalev 23.12.2016
 */

public class DetailsView extends BaseView<DetailsScreen.DetailsPresenter> implements IProductView {

    private ScreenDetailsBinding mBinding;

    public DetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<DetailsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initView() {
        mBinding = ScreenDetailsBinding.bind(this);
    }

    @Override
    public void setProduct(Product product) {
        DetailsAdapter adapter = new DetailsAdapter(getContext(), product);
    }

    @Override
    public void setViewModel(ProductViewModel viewModel) {

    }

    @Override
    public boolean viewOnBackPressed() {
        // TODO: 14.01.2017 implement
        return false;
    }

    public ViewPager getViewPager() {
        return mBinding.pager;
    }
}
