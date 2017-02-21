package ru.mikhalev.vladimir.mvpshop.features.auth;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenAuthBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.utils.UIHelper;


/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public class AuthView extends BaseView<AuthScreen.AuthPresenter> implements IAuthView, IAuthActions {

    private final Transition mBounds;
    private final Transition mFade;
    private ScreenAuthBinding mBinding;
    private AuthViewModel mViewModel;

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBounds = new ChangeBounds();
        mFade = new Fade();
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<AuthScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initView() {
        mBinding = ScreenAuthBinding.bind(this);
        mBinding.setActionsHandler(this);
    }


    //region =============== Events ==============

    @Override
    public void clickOnLogin() {
        if (mViewModel.getInputState() == AuthViewModel.STATE.IDLE) {
            showLoginWithAnim();
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
        invalidLoginAnimation();
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
    public void setViewModel(AuthViewModel viewModel) {
        mViewModel = viewModel;
        mBinding.setViewModel(mViewModel);
        if (mViewModel.getInputState() == AuthViewModel.STATE.LOGIN) {
            showLoginState();
        } else {
            showIdleState();
        }
    }


    @Override
    public boolean viewOnBackPressed() {
        if (mViewModel.getInputState() == AuthViewModel.STATE.LOGIN) {
            showIdleWithAnim();
            return true;
        }
        return false;
    }

    //endregion

    private void showLoginState() {
        LayoutParams cardParams = (LayoutParams) mBinding.authCard.getLayoutParams();
        cardParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mBinding.authCard.setLayoutParams(cardParams);
        mBinding.authCard.setCardElevation(4 * UIHelper.getDensity(getContext()));
        mBinding.authCard.getChildAt(0).setVisibility(VISIBLE);
        mBinding.showCatalogBtn.setVisibility(GONE);
        mViewModel.setInputState(AuthViewModel.STATE.LOGIN);
    }

    private void showIdleState() {
        LayoutParams cardParams = (LayoutParams) mBinding.authCard.getLayoutParams();
        cardParams.height = (int) (44 * UIHelper.getDensity(getContext()));
        mBinding.authCard.setLayoutParams(cardParams);
        mBinding.authCard.setCardElevation(0);
        mBinding.authCard.getChildAt(0).setVisibility(GONE);
        mBinding.showCatalogBtn.setVisibility(VISIBLE);
        mViewModel.setInputState(AuthViewModel.STATE.IDLE);
    }

    //region =============== Animation ==============

    private void invalidLoginAnimation() {
        // TODO: 19.02.2017 start if invalid login or password
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.invalid_field_animator);
        set.setTarget(mBinding.loginBtn);
        set.start();
    }

    private void showLoginWithAnim() {
        TransitionSet set = new TransitionSet();
        set.addTransition(mBounds)
                .addTransition(mFade)
                .setDuration(300)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        TransitionManager.beginDelayedTransition(this, set);

        showLoginState();
    }

    private void showIdleWithAnim() {
        TransitionSet set = new TransitionSet();
        Transition fade = new Fade();
        fade.addTarget(mBinding.authCard.getChildAt(0));
        set.addTransition(fade)
                .addTransition(mBounds)
                .addTransition(mFade)
                .setDuration(300)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        TransitionManager.beginDelayedTransition(this, set);

        showIdleState();
    }


    //endregion
}
