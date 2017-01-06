package ru.mikhalev.vladimir.mvpshop.features.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;

import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenAuthBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;


/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public class AuthView extends BaseView<AuthScreen.AuthPresenter> implements IAuthView, IAuthActions {

    private ScreenAuthBinding mBinding;
    private AuthViewModel mViewModel;

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<AuthScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initBinding() {
        mBinding = ScreenAuthBinding.bind(this);
    }


    //region =============== Events ==============

    @Override
    public void clickOnLogin() {
        if (mViewModel.getInputState() == AuthViewModel.STATE.IDLE) {
            mBinding.authCard.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.card_in_animation));
            mViewModel.setInputState(AuthViewModel.STATE.LOGIN);
        } else if (mViewModel.isValid()) {
            mPresenter.clickOnLogin();
        }
    }

    @Override
    public void clickOnShowCatalog() {
        mPresenter.clickOnShowCatalog();
    }

    @Override
    public void clickOnVk(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation));
        mPresenter.clickOnVk();
    }

    @Override
    public void clickOnFb(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation));
        mPresenter.clickOnFb();
    }

    @Override
    public void clickOnTwitter(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation));
        mPresenter.clickOnTwitter();
    }

    //endregion


    //region =============== IAuthView ==============


    @Override
    public void setViewModel(BaseViewModel viewModel) {
        mViewModel = (AuthViewModel) viewModel;
    }

    @Override
    public void initView() {
        mBinding.setViewModel(mViewModel);
        mBinding.setActionsHandler(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        if (mViewModel.getInputState() == AuthViewModel.STATE.LOGIN) {
            mBinding.authCard.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.card_out_animation));
            mViewModel.setInputState(AuthViewModel.STATE.IDLE);
            return true;
        }
        return false;
    }

    //endregion
}
