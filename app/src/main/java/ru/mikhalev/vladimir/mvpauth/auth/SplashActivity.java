package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.RootActivity;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseActivity;
import ru.mikhalev.vladimir.mvpauth.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity implements IAuthView, View.OnClickListener {
    private static final String TAG = "SplashActivity";
    private static final String LOADER_VISIBILE = "LOADER_VISIBILE";
    private AuthPresenter mPresenter = AuthPresenter.getInstance();
    private ActivitySplashBinding mBinding;

    //region ============== Life cycle ===============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mPresenter.takeView(this);
        mPresenter.initView();
        mBinding.setPresenter(mPresenter);

        mBinding.showCatalogBtn.setOnClickListener(this);
        mBinding.loginBtn.setOnClickListener(this);
        mBinding.fbBtn.setOnClickListener(this);
        mBinding.twBtn.setOnClickListener(this);
        mBinding.vkBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LOADER_VISIBILE, mBinding.dotLoader.isShown());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(LOADER_VISIBILE)) {
            showLoad();
        } else {
            hideLoad();
        }
    }

    //endregion

    //region =============== IView ==============
    @Override
    public void showLoad() {
        mBinding.dotLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
        mBinding.dotLoader.setVisibility(View.GONE);
    }
    //endregion

    //region ============== IAuthView ===============
    @Override
    public void showLoginBtn() {
        mBinding.loginBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginBtn() {
        mBinding.loginBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public AuthPanel getAuthPanel() {
        return mBinding.authPanel;
    }

    @Override
    public void showEmailError(boolean error) {
        if (error) {
            mBinding.email.setError(this.getString(R.string.message_error_email));
            requestFocus(mBinding.emailEt);
        } else {
            mBinding.email.setErrorEnabled(false);
        }
    }

    @Override
    public void showPasswordError(boolean error) {
        if (error) {
            mBinding.password.setError(this.getString(R.string.message_error_password));
            requestFocus(mBinding.passwordEt);
        } else {
            mBinding.password.setErrorEnabled(false);
        }
    }

    @Override
    public void showCatalogScreen() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion


    @Override
    public void onBackPressed() {
        if (!mBinding.authPanel.isIdle()) {
            mBinding.authPanel.setCustomState(AuthPanel.IDLE_STATE);
            hideLoad();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_catalog_btn:
                mPresenter.clickOnShowCatalog();
                break;
            case R.id.login_btn:
                mPresenter.clickOnLogin();
                break;
            case R.id.fb_btn:
                mPresenter.clickOnFb();
                startShakeAnimation(v);
                break;
            case R.id.tw_btn:
                mPresenter.clickOnTwitter();
                startShakeAnimation(v);
                break;
            case R.id.vk_btn:
                mPresenter.clickOnVk();
                startShakeAnimation(v);
                break;
        }
    }

    public void startShakeAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        view.startAnimation(animation);
    }


    private void requestFocus(EditText editText) {
        if (editText.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            editText.setSelection(editText.length());
        }
    }
}
