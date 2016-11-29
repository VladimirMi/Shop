package ru.mikhalev.vladimir.mvpauth.account;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.databinding.ScreenAccountBinding;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountView extends CoordinatorLayout implements IAccountView, IAccountActions {

    private ScreenAccountBinding mBinding;
    private AccountScreen mScreen;
    private AccountDto mAccountDto;
    @Inject AccountScreen.AccountPresenter mPresenter;

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mBinding = ScreenAccountBinding.bind(this);
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
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }
    //endregion


    // FIXME: 29.11.2016 databinding
//    private void showViewFromState() {
//
//    }
//
//    public void initView(AccountDto accountDto) {
//        mAccountDto = accountDto;
//        initProfileInfo();
//        initList();
//        initSettings();
//        showViewFromState();
//    }
//
//    private void initProfileInfo() {
//
//    }
//
//    private void initList() {
//
//    }
//
//    private void initSettings() {
//
//    }


    //region ==================== IAccountView ========================

    @Override
    public void changeState() {

    }

    @Override
    public void showEditState() {

    }

    @Override
    public void showPreviewState() {

    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPhone() {
        return null;
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion
}
