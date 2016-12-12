package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import flow.Flow;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.databinding.ScreenAuthBinding;
import timber.log.Timber;


/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public class AuthView extends RelativeLayout implements IAuthView, IAuthActions {

    @Inject
    AuthPresenter mPresenter;

    private ScreenAuthBinding mBinding;
    private AuthViewModel mViewModel;

    public AuthViewModel getViewModel() {
        return mViewModel;
    }

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Timber.d("AuthView");
        if (!isInEditMode()) {
            DaggerService.<AuthScreen.Component>getDaggerComponent(context).inject(this);
            AuthScreen screen = Flow.getKey(this);
            if (screen != null) {
                mViewModel = screen.getViewModel();
            }
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Timber.d("onFinishInflate");
        mBinding = ScreenAuthBinding.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Timber.d("onAttachedToWindow");
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Timber.d("onDetachedFromWindow");
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }

    //endregion


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
