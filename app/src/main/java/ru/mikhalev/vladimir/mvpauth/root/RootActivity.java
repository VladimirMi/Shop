package ru.mikhalev.vladimir.mvpauth.root;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import ru.mikhalev.vladimir.mvpauth.BuildConfig;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.account.AccountScreen;
import ru.mikhalev.vladimir.mvpauth.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.AppComponent;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.RootScope;
import ru.mikhalev.vladimir.mvpauth.core.layers.view.BaseActivity;
import ru.mikhalev.vladimir.mvpauth.core.layers.view.IView;
import ru.mikhalev.vladimir.mvpauth.core.utils.MyGlideModule;
import ru.mikhalev.vladimir.mvpauth.databinding.ActivityRootBinding;
import ru.mikhalev.vladimir.mvpauth.databinding.ToolbarBasketItemBinding;
import ru.mikhalev.vladimir.mvpauth.flow.TreeKeyDispatcher;
import ru.mikhalev.vladimir.mvpauth.splash.SplashActivity;

public class RootActivity extends BaseActivity implements IRootView, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "RootActivity";
    private ProgressDialog mProgressDialog;
    private ActivityRootBinding mBinding;
    private ToolbarBasketItemBinding mBasketBinding;
    @Inject RootPresenter mPresenter;
    private boolean isExitEnabled = false;

    //region ==================== Life cycle ========================


    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new AccountScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();
        super.attachBaseContext(newBase);
    }

    @Override
    public Object getSystemService(String name) {
        MortarScope rootActivityScope = MortarScope.findChild(getApplicationContext(), this.getClass().getName());
        if (rootActivityScope.hasService(name)) {
            return rootActivityScope.getService(name);
        } else {
            return super.getSystemService(name);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_root);

        mBasketBinding = ToolbarBasketItemBinding.inflate(getLayoutInflater(),
                (ViewGroup) mBinding.getRoot(), false);
        mBasketBinding.setCount(0);

        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);

        DaggerService.getComponent(RootActivity.Component.class,
                new RootActivity.Module()).inject(this);

        mPresenter.takeView(this);
        mPresenter.initView();
//        mBinding.appbar.setVisibility(View.GONE);
//        mBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        initToolbar();
        initDrawer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        mPresenter.takeView(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mPresenter.dropView();
        super.onPause();
    }

    //endregion


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalog_menu, menu);
        MenuItem item = menu.findItem(R.id.basket);
        MenuItemCompat.setActionView(item, mBasketBinding.getRoot());
        MenuItemCompat.getActionView(item).setOnClickListener(v -> {
            // TODO: 07.11.16 open basket view
            showMessage(getString(R.string.catalog_add));
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawer, mBinding.toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mBinding.drawer.addDrawerListener(toggle);
        mBinding.navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
        ImageView avatar = (ImageView) mBinding.navigationView.getHeaderView(0).findViewById(R.id.avatar);
        MyGlideModule.setUserAvatar(R.drawable.user_avatar, avatar);
    }

    @Override
    public void onBackPressed() {
        if (getCurrentScreen() != null && !getCurrentScreen().viewOnBackPressed() && Flow.get(this).goBack()) {
            super.onBackPressed();
        }
//        if (mBinding.drawer.isDrawerOpen(GravityCompat.START)) {
//            mBinding.drawer.closeDrawer(GravityCompat.START);
//        } else if (fragment != null && !fragment.isVisible()) {
//            mBinding.navigationView.setCheckedItem(R.id.nav_catalog);
//        } else if (isExitEnabled) {
//            super.onBackPressed();
//        } else {
//            isExitEnabled = true;
//            new Handler().postDelayed(() -> isExitEnabled = false, 2000);
//            showMessage(getString(R.string.message_exit));
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Object key = null;
        switch (item.getItemId()) {
            case R.id.nav_accout:
                key = new AccountScreen();
                break;

            case R.id.nav_catalog:
                key = new CatalogScreen();
                break;

            case R.id.nav_favorite:
                break;

            case R.id.nav_orders:
                break;

            case R.id.nav_notifications:
                break;
        }

        if (key != null) {
            Flow.get(this).set(key);
        }

        item.setChecked(true);
        mBinding.drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //region ==================== IRootView ========================

    @Override
    public void showMessage(String message) {
        Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        showMessage(e.getLocalizedMessage());
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        } else {
            // TODO: 20-Oct-16 send error stacktrace to crashlytics
        }
    }

    @Override
    public void showLoad() {
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.progress_show_text),
                true, false);
    }

    @Override
    public void hideLoad() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Nullable
    @Override
    public IView getCurrentScreen() {
        return (IView) mBinding.rootFrame.getChildAt(0);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    public void setBasketCounter(int count) {
        mBasketBinding.setCount(count);
    }

    //endregion


    //region ==================== DI ========================

    @dagger.Module
    public static class Module {
        @Provides
        @RootScope
        RootPresenter provideRootPresenter() {
            return new RootPresenter();
        }
    }

    @dagger.Component(dependencies = AppComponent.class, modules = Module.class)
    @RootScope
    public interface Component {
        void inject(RootActivity view);

        void inject(SplashActivity view);
        RootPresenter getRootPresenter();
    }
    //endregion
}
