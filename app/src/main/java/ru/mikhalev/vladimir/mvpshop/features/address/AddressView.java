package ru.mikhalev.vladimir.mvpshop.features.address;

import android.content.Context;
import android.util.AttributeSet;

import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenAddressBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */
public class AddressView extends BaseView<AddressScreen.AddressPresenter> implements IAddressView, IAddressActions {

    private ScreenAddressBinding mBinding;
    private AddressViewModel mViewModel;

    public AddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<AddressScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initBinding() {
        mBinding = ScreenAddressBinding.bind(this);
    }

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
        // TODO: 04.01.2017 move to viewModel
        mBinding.addAddress.setText(getContext().getString(R.string.address_save));
    }

    @Override
    public void showInputError() {

    }

    // TODO: 04.01.2017 remove
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
