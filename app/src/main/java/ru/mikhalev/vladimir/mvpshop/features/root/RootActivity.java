package ru.mikhalev.vladimir.mvpshop.features.root;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import ru.mikhalev.vladimir.mvpshop.BuildConfig;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.App;
import ru.mikhalev.vladimir.mvpshop.core.BaseActivity;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.databinding.ActivityRootBinding;
import ru.mikhalev.vladimir.mvpshop.databinding.DrawerHeaderBinding;
import ru.mikhalev.vladimir.mvpshop.databinding.ToolbarBasketItemBinding;
import ru.mikhalev.vladimir.mvpshop.di.components.AppComponent;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountModel;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountScreen;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountViewModel;
import ru.mikhalev.vladimir.mvpshop.features.auth.AuthScreen;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpshop.flow.TreeKeyDispatcher;
import ru.mikhalev.vladimir.mvpshop.utils.UIHelper;
import timber.log.Timber;

public class RootActivity extends BaseActivity implements IRootView, NavigationView.OnNavigationItemSelectedListener, IActionBarView {
    private static final int REQUEST_SETTINGS_INTENT = 123;

    @Inject RootPresenter mRootPresenter;

    private ProgressDialog mProgressDialog;
    private ActivityRootBinding mBinding;
    private DrawerHeaderBinding mDrawerBinding;
    private ToolbarBasketItemBinding mBasketBinding;
    private ActionBarDrawerToggle mToggle;
    private ActionBar mActionBar;
    private List<MenuItemHolder> mActionBarMenuItems;

    //region ==================== Life cycle ========================


    @Override
    protected void attachBaseContext(Context newBase) {
        Timber.e("attachBaseContext: ");
        newBase = Flow.configure(newBase, this)
                .defaultKey(new AuthScreen())
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

        App.getRootActivityComponent().inject(this);

        mRootPresenter.takeView(this);
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
        mRootPresenter.takeView(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mRootPresenter.dropView(this);
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
        mActionBar = getSupportActionBar();
    }

    private void initDrawer() {
        mToggle = new ActionBarDrawerToggle(
                this, mBinding.drawer, mBinding.toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mToggle.syncState();
        mBinding.drawer.addDrawerListener(mToggle);
        mBinding.navigationView.setNavigationItemSelectedListener(this);

        mDrawerBinding = DrawerHeaderBinding.inflate(getLayoutInflater(), mBinding.navigationView, false);
        mBinding.navigationView.addHeaderView(mDrawerBinding.getRoot());
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawer.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (getCurrentScreen() != null && !getCurrentScreen().viewOnBackPressed() && !Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Object key = null;
        switch (item.getItemId()) {
            case R.id.nav_account:
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
    public void setDrawer(AccountViewModel accountViewModel) {
        mDrawerBinding.setViewModel(accountViewModel);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        showMessage(e.getLocalizedMessage());
        if (BuildConfig.DEBUG) {
            Timber.e(e, e.getLocalizedMessage());
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
    public BaseView getCurrentScreen() {
        return (BaseView) mBinding.rootFrame.getChildAt(0);
    }


    @Override
    public void setBasketCounter(int count) {
        mBasketBinding.setCount(count);
    }

    @Override
    public void showPermissionSnackBar() {
        Snackbar.make(mBinding.getRoot(), "Для корректной работы необходимо дать требуемые разрешения", Snackbar.LENGTH_LONG)
                .setAction("Разрешить", view -> openApplicationSettings()).show();
    }

    @Override
    public void openApplicationSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_SETTINGS_INTENT);
    }

    @Override
    public void hideToolbar() {
        getSupportActionBar().hide();
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mBinding.rootFrame.getLayoutParams();
        lp.setMargins(0, 0, 0, 0);
        mBinding.rootFrame.setLayoutParams(lp);
    }

    @Override
    public void lockDrawer() {
        mBinding.drawer.closeDrawer(GravityCompat.START);
        mBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void showToolbar() {
        getSupportActionBar().show();
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mBinding.rootFrame.getLayoutParams();
        lp.setMargins(0, UIHelper.getActionBarHeight(this), 0, 0);
        mBinding.rootFrame.setLayoutParams(lp);
    }

    @Override
    public void unlockDrawer() {
        mBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    //endregion


    //region =============== IActionBarView ==============


    @Override
    public void setToolbarVisible(boolean isVisible) {
        // TODO: 05.01.2017 сделать через show/hide
        if (isVisible) {
            mBinding.toolbar.setVisibility(View.VISIBLE);
        } else {
            mBinding.toolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitle(String title) {
        mBinding.toolbar.setTitle(title);
    }

    @Override
    public void setBackArrow(boolean enabled) {
        if (mToggle != null && mActionBar != null) {
            if (enabled) {
                mToggle.setDrawerIndicatorEnabled(false);
                mActionBar.setDisplayHomeAsUpEnabled(true);
                if (mToggle.getToolbarNavigationClickListener() == null) {
                    mToggle.setToolbarNavigationClickListener(v -> onBackPressed());
                }
                lockDrawer();
            } else {
                mToggle.setDrawerIndicatorEnabled(true);
                mToggle.setToolbarNavigationClickListener(null);
                mActionBar.setDisplayHomeAsUpEnabled(false);
                unlockDrawer();
            }
            mToggle.syncState();
        }
    }

    @Override
    public void setMenuItems(List<MenuItemHolder> items) {
        mActionBarMenuItems = items;
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mActionBarMenuItems != null && !mActionBarMenuItems.isEmpty()) {
            for (MenuItemHolder menuItem : mActionBarMenuItems) {
                MenuItem item = menu.add(menuItem.getItemTitle());
                item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
                        .setIcon(menuItem.getIconResId())
                        .setOnMenuItemClickListener(menuItem.getListener());
            }
        } else {
            menu.clear();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTabLayout(ViewPager pager) {
        // TODO: 05.01.2017 табы на всю ширину в лэндскейпе
        TabLayout tabs = new TabLayout(this);
        tabs.setupWithViewPager(pager);
        mBinding.appbar.addView(tabs);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    @Override
    public void removeTabLayout() {
        View tabs = mBinding.appbar.getChildAt(1);
        if (tabs != null && tabs instanceof TabLayout) {
            mBinding.appbar.removeView(tabs);
        }
    }

    //endregion


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRootPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRootPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //region ==================== DI ========================

    @dagger.Module
    public static class Module {
        @Provides
        @DaggerScope(RootActivity.class)
        RootPresenter provideRootPresenter() {
            return new RootPresenter();
        }

        @Provides
        @DaggerScope(RootActivity.class)
        AccountModel provideAccountModel() {
            return new AccountModel();
        }
    }

    @dagger.Component(dependencies = AppComponent.class, modules = RootActivity.Module.class)
    @DaggerScope(RootActivity.class)
    public interface Component {
        void inject(RootActivity view);

        void inject(RootPresenter view);

        RootPresenter getRootPresenter();

        AccountModel getAccountModel();
    }

    //endregion
}
