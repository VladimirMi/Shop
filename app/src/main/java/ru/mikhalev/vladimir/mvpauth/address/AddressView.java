package ru.mikhalev.vladimir.mvpauth.address;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.databinding.ScreenAddressBinding;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */
public class AddressView extends RelativeLayout implements IAddressView {
    @Inject
    AddressScreen.AddressPresenter mPresenter;

    private ScreenAddressBinding mBinding;

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

    void clickOnAddAddress() {
        mPresenter.clickOnAddAddress();
    }

    //endregion

    //region =============== IAddressView ==============

    @Override
    public void showInputError() {

    }

    @Override
    public AddressViewModel getUserAddress() {
        return null;
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
