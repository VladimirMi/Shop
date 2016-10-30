package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bhargavms.dotloader.DotLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.RootActivity;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseActivity;
import ru.mikhalev.vladimir.mvpauth.core.utils.ConstantManager;

public class SplashActivity extends BaseActivity implements IAuthView, View.OnClickListener {
    private AuthPresenter mPresenter = AuthPresenter.getInstance();

    @BindView(R.id.coordinator_container) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.auth_wrapper) AuthPanel mAuthPanel;

    @BindView(R.id.login_email_et) EditText mEmailEt;
    @BindView(R.id.login_password_et) EditText mPasswordEt;

    @BindView(R.id.login_email_til) TextInputLayout mEmailTil;
    @BindView(R.id.login_password_til) TextInputLayout mPasswordTil;

    @BindView(R.id.show_catalog_btn) Button mShowCatalogBtn;
    @BindView(R.id.login_btn) Button mLoginBtn;

    @BindView(R.id.fb_btn) ImageButton mFbBtn;
    @BindView(R.id.tw_btn) ImageButton mTwBtn;
    @BindView(R.id.vk_btn) ImageButton mVkBtn;

    @BindView(R.id.dot_loader) DotLoader mDotLoader;

    //region ============== Life cycle ===============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mPresenter.takeView(this);
        mPresenter.initView();

        mShowCatalogBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mFbBtn.setOnClickListener(this);
        mTwBtn.setOnClickListener(this);
        mVkBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ConstantManager.LOADER_VISIBILE, mDotLoader.isShown());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(ConstantManager.LOADER_VISIBILE)) {
            showLoad();
        } else {
            hideLoad();
        }
    }

    //endregion

    //region =============== IView ==============
    @Override
    public void showLoad() {
        mDotLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
        mDotLoader.setVisibility(View.GONE);
    }
    //endregion

    //region ============== IAuthView ===============
    @Override
    public void showLoginBtn() {
        mLoginBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginBtn() {
        mLoginBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public AuthPanel getAuthPanel() {
        return mAuthPanel;
    }

    @Override
    public void showEmailError(boolean error) {
        if (error) {
            mEmailTil.setError(this.getString(R.string.err_msg_email));
            requestFocus(mEmailEt);
        } else {
            mEmailTil.setErrorEnabled(false);
        }
    }

    @Override
    public void showPasswordError(boolean error) {
        if (error) {
            mPasswordTil.setError(this.getString(R.string.err_msg_password));
            requestFocus(mPasswordEt);
        } else {
            mPasswordTil.setErrorEnabled(false);
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
        if (!mAuthPanel.isIdle()) {
            mAuthPanel.setCustomState(ConstantManager.IDLE_STATE);
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
