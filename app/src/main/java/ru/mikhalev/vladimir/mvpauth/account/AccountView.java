package ru.mikhalev.vladimir.mvpauth.account;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import flow.Flow;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.databinding.ScreenAccountBinding;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountView extends CoordinatorLayout implements IAccountView, IAccountActions {
    private static final String TAG = "AccountView";

    @IntDef({STATE.IDLE, STATE.EDIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        int IDLE = 0;
        int EDIT = 1;
    }

    @Inject AccountScreen.AccountPresenter mPresenter;
    private ScreenAccountBinding mBinding;
    private AccountScreen mScreen;
    private AccountViewModel mAccountViewModel;

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<AccountScreen.Component>getDaggerComponent(getContext()).inject(this);
            mScreen = Flow.getKey(this);
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            mBinding = ScreenAccountBinding.bind(this);
            mBinding.setActionsHandler(this);
            showViewFromState();
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
    public void switchViewState() {
        mPresenter.switchViewState();
    }

    @Override
    public void clickOnAddress() {
        mPresenter.clickOnAddress();
    }

    @Override
    public void clickOnAddAddress() {
        mPresenter.clickOnAddAddress();
    }

    @Override
    public void switchOrder(boolean isCheked) {
        mPresenter.switchOrder(isCheked);
    }

    @Override
    public void switchPromo(boolean isCheked) {
        mPresenter.switchPromo(isCheked);
    }

    @Override
    public void takePhoto() {
        mPresenter.takePhoto();
    }

    // TODO: 01.12.2016 remove with swipe
    @Override
    public void removeAddress() {
        mPresenter.removeAddress();
    }

    //endregion

    private void showViewFromState() {
        mBinding.setViewState(mScreen.getViewState());
    }

    public void initView(AccountViewModel viewModel) {
        Log.e(TAG, "initView: with path " + viewModel.getAvatar());
        mAccountViewModel = viewModel;
        mBinding.setViewModel(mAccountViewModel);
    }

    //region ==================== IAccountView ========================

    @Override
    public void changeState() {
        if (mScreen.getViewState() == STATE.EDIT) {
            mScreen.setViewState(STATE.IDLE);
        } else {
            mScreen.setViewState(STATE.EDIT);
        }
        showViewFromState();
    }

    @Override
    public void showPhotoSourceDialog() {
        // FIXME: 01.12.2016 refactoring
        String[] source = {"Загрузить из галереи", "Сделать фото", "Отмена"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Установить фото");
        alertDialog.setItems(source, (dialog, i) -> {
            switch (i) {
                case 0:
                    mPresenter.chooseGallery();
                    break;
                case 1:
                    mPresenter.chooseCamera();
                    break;
                case 2:
                    dialog.cancel();
                    break;
            }
        });
        alertDialog.show();
    }

    @Override
    public String getUserName() {
        return mAccountViewModel.getFullname();
    }

    @Override
    public String getUserPhone() {
        return mAccountViewModel.getPhone();
    }

    @Override
    public boolean viewOnBackPressed() {
        if (mScreen.getViewState() == STATE.EDIT) {
            changeState();
            return true;
        }
        return false;
    }

    //endregion
}
