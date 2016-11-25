package ru.mikhalev.vladimir.mvpauth.product;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseFragment;
import ru.mikhalev.vladimir.mvpauth.databinding.FragmentProductBinding;
import ru.mikhalev.vladimir.mvpauth.di.scopes.ProductScope;

/**
 * Developer Vladimir Mikhalev 28.10.2016
 */

public class ProductFragment extends BaseFragment implements IProductView, View.OnClickListener {
    private static final String TAG = "ProductFragment";
    private FragmentProductBinding mBinding;
    @Inject
    ProductPresenter mPresenter;

    public ProductFragment() {
    }

    public static ProductFragment newInstance(ProductDto product) {
        Bundle args = new Bundle();
        args.putParcelable("PRODUCT", product);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void readArguments(Bundle bundle) {
        if (bundle != null) {
            ProductDto product = bundle.getParcelable("PRODUCT");

            // FIXME: 06.11.2016 recreate component

            DaggerProductFragment_Component.builder()
                    .module(new ProductFragment.Module(product))
                    .build().inject(this);
        }
    }

    //region ==================== Life cycle ========================

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        readArguments(getArguments());
        mPresenter.takeView(this);
        mPresenter.initView();
        mBinding.minus.setOnClickListener(this);
        mBinding.plus.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onStop() {
        mPresenter.dropView();
        super.onStop();
    }

    //endregion

    //region ==================== IProductView ========================
    @Override
    public void showProductView(ProductDto product) {
        mBinding.setProduct(product);
    }

    @Override
    public void updateProductCountView(ProductDto product) {
        mBinding.setProduct(product);
    }
    //endregion


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.minus:
                mPresenter.clickOnMinus();
                break;
            case R.id.plus:
                mPresenter.clickOnPlus();
                break;
        }
    }

    //region ==================== DI ========================

    @dagger.Module
    public static class Module {
        private ProductDto mProduct;

        public Module(ProductDto product) {
            mProduct = product;
        }

        @Provides
        @ProductScope
        ProductPresenter provideCatalogPresenter() {
            return new ProductPresenter(mProduct);
        }
    }

    @dagger.Component(modules = Module.class)
    @ProductScope
    interface Component {
        void inject(ProductFragment view);
    }

    //endregion
}
