package ru.mikhalev.vladimir.mvpshop.features.address;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenAddressBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */
public class AddressView extends RelativeLayout implements IAddressView, IAddressActions {
    @Inject
    AddressScreen.AddressPresenter mPresenter;
    private ScreenAddressBinding mBinding;
    private AddressViewModel mViewModel;

    public AddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<AddressScreen.Component>getDaggerComponent(context).inject(this);
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mBinding = ScreenAddressBinding.bind(this);
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
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }
    //endregion

    //region =============== Events ==============

    @Override
    public void clickOnAddAddress() {
        mPresenter.clickOnAddAddress();
    }

    //endregion


    //region =============== IAddressView ==============


    @Override
    public void setViewModel(BaseViewModel viewModel) {
        mViewModel = (AddressViewModel) viewModel;
        mBinding.setViewModel(mViewModel);
        mBinding.setActionsHandler(this);
    }

    @Override
    public void initView() {
        mBinding.addAddress.setText(getContext().getString(R.string.address_save));
    }

    @Override
    public void showInputError() {

    }

    @Override
    public AddressViewModel getUserAddress() {
        return mBinding.getViewModel();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
