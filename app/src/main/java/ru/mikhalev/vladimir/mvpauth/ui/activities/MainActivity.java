package ru.mikhalev.vladimir.mvpauth.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import ru.mikhalev.vladimir.mvpauth.BuildConfig;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.mvp.presenters.AuthPresenter;
import ru.mikhalev.vladimir.mvpauth.mvp.presenters.IAuthPresenter;
import ru.mikhalev.vladimir.mvpauth.mvp.views.IAuthView;
import ru.mikhalev.vladimir.mvpauth.ui.views.AuthPanel;
import ru.mikhalev.vladimir.mvpauth.utils.ConstantManager;

public class MainActivity extends AppCompatActivity implements IAuthView, View.OnClickListener {
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
        setContentView(R.layout.activity_main);
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

    //region ============== IAuthView ===============

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Извините что то пошло не так, попрбуйте позже");
            // TODO: 20-Oct-16 send error stacktrace to crashlytics
        }
    }

    @Override
    public void showLoad() {
        mDotLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
        mDotLoader.setVisibility(View.GONE);
    }

    @Override
    public IAuthPresenter getPresenter() {
        return mPresenter;
    }

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
    public void startFbAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        mFbBtn.startAnimation(animation);
    }

    @Override
    public void startTwAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        mTwBtn.startAnimation(animation);
    }

    @Override
    public void startVkAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        mVkBtn.startAnimation(animation);
    }

    @Override
    public void requestEmailFocus() {
        requestFocus(mEmailEt);
    }

    @Override
    public void requestPasswordFocus() {
        requestFocus(mPasswordEt);
    }

    @Override
    public void setEmailError(String error) {
        if (error != null) {
            mEmailTil.setError(error);
        } else {
            mEmailTil.setErrorEnabled(false);
        }
    }

    @Override
    public void setPasswordError(String error) {
        if (error != null) {
            mPasswordTil.setError(error);
        } else {
            mPasswordTil.setErrorEnabled(false);
        }
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
                break;
            case R.id.tw_btn:
                mPresenter.clickOnTwitter();
                break;
            case R.id.vk_btn:
                mPresenter.clickOnVk();
                break;
        }
    }

    /**
     * Запрос фокуса у EditText и если возможно, установка его и перемещение курсора
     * в конец строки
     *
     * @param editText EditText у которого запрашивается фокус
     */
    private void requestFocus(EditText editText) {
        if (editText.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            editText.setSelection(editText.length());
        }
    }
}
