package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import javax.inject.Inject;

import flow.Flow;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AuthScope;
import ru.mikhalev.vladimir.mvpauth.core.layers.view.BaseActivity;
import ru.mikhalev.vladimir.mvpauth.databinding.ActivitySplashBinding;
import ru.mikhalev.vladimir.mvpauth.flow.TreeKeyDispatcher;
import ru.mikhalev.vladimir.mvpauth.home.RootActivity;

public class SplashActivity extends BaseActivity implements IAuthView {
    private static final String TAG = "SplashActivity";
    private static final String LOADER_VISIBILE = "LOADER_VISIBILE";

    @Inject
    AuthPresenter mPresenter;
    private ActivitySplashBinding mBinding;

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new AuthScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();
        super.attachBaseContext(newBase);
    }

    //region ============== Life cycle ===============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        if (isFinishing()) {
            DaggerService.unregisterScope(AuthScope.class);
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LOADER_VISIBILE, mBinding.loader.isShown());
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
        mBinding.loader.show();
    }

    @Override
    public void hideLoad() {
        mBinding.loader.hide();
    }
    //endregion

    //region ============== IAuthView ===============

    @Override
    public void showCatalogScreen() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion


    private void requestFocus(EditText editText) {
        if (editText.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            editText.setSelection(editText.length());
        }
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}