package ru.mikhalev.vladimir.mvpauth.catalog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.CatalogScope;
import ru.mikhalev.vladimir.mvpauth.core.layers.BaseFragment;
import ru.mikhalev.vladimir.mvpauth.databinding.FragmentCatalogBinding;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogFragment extends BaseFragment implements ICatalogView, View.OnClickListener {
    public static final String TAG = "CatalogFragment";
    @Inject
    CatalogPresenter mPresenter;
    private FragmentCatalogBinding mBinding;

    public CatalogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_catalog, container, false);

        DaggerService.getComponent(CatalogFragment.Component.class,
                new CatalogFragment.Module()).inject(this);
        mPresenter.takeView(this);
        mPresenter.initView();
        mBinding.addToCardBtn.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_to_card_btn) {
            mPresenter.clickOnBuyButton(mBinding.productPager.getCurrentItem());
        }
    }

    //region =============== ICatalogView ==============
    @Override
    public void showAddToCartMessage(ProductDto product) {
        showMessage("Товар " + product.getProductName() + " успешно добавлен в корзину");
    }

    @Override
    public void showCatalogView(List<ProductDto> productList) {
        CatalogAdapter adapter = new CatalogAdapter(getChildFragmentManager());
        for (ProductDto product : productList) {
            adapter.addItem(product);
        }
        mBinding.productPager.setAdapter(adapter);
    }

    @Override
    public void showAuthScreen() {
//        getRootActivity().showAuthFragment();
    }

    @Override
    public void updateProductCounter(int count) {
        getRootActivity().setBasketCounter(count);
    }
    //endregion

    //region ==================== DI ========================

    @dagger.Module
    public static class Module {
        @Provides
        @CatalogScope
        CatalogPresenter provideCatalogPresenter() {
            return new CatalogPresenter();
        }
    }

    @dagger.Component(modules = Module.class)
    @CatalogScope
    interface Component {
        void inject(CatalogFragment view);
    }

    //endregion
}
