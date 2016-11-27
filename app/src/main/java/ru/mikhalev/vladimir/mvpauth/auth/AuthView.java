package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AuthScope;
import ru.mikhalev.vladimir.mvpauth.databinding.ScreenAuthBinding;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public class AuthView extends RelativeLayout implements IAuthView {

    @Inject
    AuthScreen.AuthPresenter mPresenter;

    @Inject
    AuthViewModel mViewModel;

    private ScreenAuthBinding mBinding;
    private int mInputState;

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO: 27.11.2016 get mScreen and dagger components
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBinding = ScreenAuthBinding.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }

    //endregion

    //region =============== Events ==============

    public void clickOnLogin() {
        if (mViewModel.getEmail().isEmpty() && mViewModel.getPassword().isEmpty()) {
            mInputState = AuthInputState.LOGIN;
            mBinding.setInputState(mInputState);
        } else if (mViewModel.isValid()) {
            mPresenter.clickOnLogin(mViewModel);
        }
    }

    public void clickOnShowCatalog() {
        mInputState = AuthInputState.IDLE;
        mBinding.setInputState(mInputState);
        showCatalogScreen();
        DaggerService.unregisterScope(AuthScope.class);
    }

    public void clickOnVk(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation));
        mPresenter.clickOnVk();
    }

    public void clickOnFb(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation));
        mPresenter.clickOnFb();
    }

    public void clickOnTwitter(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_animation));
        mPresenter.clickOnTwitter();
    }
    //endregion

    //region =============== IAuthView ==============

    @Override
    public void showCatalogScreen() {

    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }


    //endregion
}
