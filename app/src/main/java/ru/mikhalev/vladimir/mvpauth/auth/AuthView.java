package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import flow.Flow;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.databinding.ScreenAuthBinding;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public class AuthView extends RelativeLayout implements IAuthView {
    private AuthScreen mScreen;

    @Inject
    AuthScreen.AuthPresenter mPresenter;

    @Inject
    AuthViewModel mViewModel;

    private ScreenAuthBinding mBinding;
    private int mInputState;

    private Animation mCardInAnimation = AnimationUtils.loadAnimation(getContext(),
            R.anim.card_in_animation);
    private Animation mCardOutAnimation = AnimationUtils.loadAnimation(getContext(),
            R.anim.card_out_animation);

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            mScreen = Flow.getKey(this);
            DaggerService.<AuthScreen.Component>getDaggerComponent(context).inject(this);
            if (mScreen != null) {
                mInputState = mScreen.getInputState();
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
    public void clickOnLogin() {
        if (mInputState == AuthInputState.IDLE) {
            mBinding.authCard.startAnimation(mCardInAnimation);
            mInputState = AuthInputState.LOGIN;
            mBinding.setInputState(mInputState);
        } else if (mViewModel.isValid()) {
            mPresenter.clickOnLogin(mViewModel);
        }
    }

    public void clickOnShowCatalog() {
        mPresenter.clickOnShowCatalog();
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
//        DaggerService.unregisterScope(AuthScope.class);
//        Flow.get(this).set(new CatalogScreen());
    }

    @Override
    public boolean viewOnBackPressed() {
        if (mInputState == AuthInputState.LOGIN) {
            mBinding.authCard.startAnimation(mCardOutAnimation);
            mInputState = AuthInputState.IDLE;
            mBinding.setInputState(mInputState);
            return true;
        }
        return false;
    }

    //endregion
}
