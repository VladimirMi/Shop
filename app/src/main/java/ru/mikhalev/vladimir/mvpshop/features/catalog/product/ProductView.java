package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.content.Context;
import android.util.AttributeSet;

import flow.Flow;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenProductBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.features.details.DetailsScreen;
import timber.log.Timber;


/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
public class ProductView extends BaseView<ProductScreen.ProductPresenter> implements IProductView, IProductActions {
    private ScreenProductBinding mBinding;
    private ProductRealm mProductRealm;
    private ProductViewModel mViewModel;

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<ProductScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initView() {
        mBinding = ScreenProductBinding.bind(this);
        mBinding.setActionsHandler(this);
    }

    //region ==================== Events ========================

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
    public void clickOnShowMore() {
        Flow.get(this).set(new DetailsScreen(mProductRealm.getId()));
    }

    @Override
    public void clickOnFavorite() {
        mProductRealm.switchFavorite();
        mPresenter.saveProduct(mProductRealm);
    }


    //endregion

    //region ==================== IProductView ========================

    @Override
    public void setProduct(ProductRealm productRealm) {
        Timber.e("setProduct: ");
        mProductRealm = productRealm;
        if (mViewModel == null) {
            mViewModel = new ProductViewModel();
            mViewModel.update(productRealm);
            mBinding.setViewModel(mViewModel);
        } else {
            mViewModel.update(productRealm);
        }
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
