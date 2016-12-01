package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import flow.Flow;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.databinding.ScreenAuthBinding;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public class AuthView extends RelativeLayout implements IAuthView, IAuthActions {

    @IntDef({
            STATE.LOGIN,
            STATE.IDLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        int LOGIN = 0;
        int IDLE = 1;
    }

    @Inject AuthScreen.AuthPresenter mPresenter;
    @Inject AuthViewModel mViewModel;
    private ScreenAuthBinding mBinding;
    private int mInputState;


    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            AuthScreen screen = Flow.getKey(this);
            DaggerService.<AuthScreen.Component>getDaggerComponent(context).inject(this);
            if (screen != null) {
                mInputState = screen.getInputState();
            }
        }
    }

    //region =============== Lifecycle ==============

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBinding = ScreenAuthBinding.bind(this);
        mBinding.setViewModel(mViewModel);
        mBinding.setInputState(mInputState);
        mBinding.setActionsHandler(this);
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
    @Override
    public void clickOnLogin() {
        if (mInputState == STATE.IDLE) {
            mBinding.authCard.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.card_in_animation));
            mInputState = STATE.LOGIN;
            mBinding.setInputState(mInputState);
        } else if (mViewModel.isValid()) {
            mPresenter.clickOnLogin(mViewModel);
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
    public void showCatalogScreen() {
    }

    @Override
    public boolean viewOnBackPressed() {
        if (mInputState == STATE.LOGIN) {
            mBinding.authCard.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.card_out_animation));
            mInputState = STATE.IDLE;
            mBinding.setInputState(mInputState);
            return true;
        }
        return false;
    }

    //endregion
}
