package ru.mikhalev.vladimir.mvpauth.root;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.ImageView;

import javax.inject.Inject;

import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.account.AccountFragment;
import ru.mikhalev.vladimir.mvpauth.auth.AuthFragment;
import ru.mikhalev.vladimir.mvpauth.catalog.CatalogFragment;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseActivity;
import ru.mikhalev.vladimir.mvpauth.core.utils.MyGlideModule;
import ru.mikhalev.vladimir.mvpauth.databinding.ActivityRootBinding;
import ru.mikhalev.vladimir.mvpauth.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.di.scopes.RootScope;

public class RootActivity extends BaseActivity implements IRootView, NavigationView.OnNavigationItemSelectedListener {
    private ActivityRootBinding mBinding;
    @Inject RootPresenter mPresenter;
    private FragmentManager mFragmentManager;
    private boolean isExitEnabled = false;

    //region ==================== Life cycle ========================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_root);
        DaggerService.getComponent(RootActivity.Component.class,
                new RootActivity.Module()).inject(this);
        mPresenter.takeView(this);

        initToolbar();
        initDrawer();

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            replaceFragment(new CatalogFragment(), CatalogFragment.TAG, false);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    //endregion

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawer, mBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawer.addDrawerListener(toggle);
        mBinding.navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
        ImageView avatar = (ImageView) mBinding.navigationView.getHeaderView(0).findViewById(R.id.avatar);
        MyGlideModule.setUserAvatar(R.drawable.user_avatar, avatar);
    }

    @Override
    public void onBackPressed() {
        CatalogFragment fragment = (CatalogFragment) mFragmentManager.findFragmentByTag(CatalogFragment.TAG);

        if (mBinding.drawer.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawer.closeDrawer(GravityCompat.START);
        } else if (fragment != null && !fragment.isVisible()) {
            mFragmentManager.popBackStack();
            mBinding.navigationView.setCheckedItem(R.id.nav_catalog);
        } else if (isExitEnabled) {
            super.onBackPressed();
        } else {
            isExitEnabled = true;
            new Handler().postDelayed(() -> isExitEnabled = false, 2000);
            showMessage(getString(R.string.message_exit));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_accout:
                if (!item.isChecked()) {
                    replaceFragment(new AccountFragment(), AccountFragment.TAG, true);
                }
                break;

            case R.id.nav_catalog:
                if (!item.isChecked()) {
                    mFragmentManager.popBackStack();
                }
                break;

            case R.id.nav_favorite:
                break;

            case R.id.nav_orders:
                break;

            case R.id.nav_notifications:
                break;
        }

        item.setChecked(true);
        mBinding.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction ft = mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        isExitEnabled = false;
    }

    public void showAuthFragment() {
        AuthFragment fragment = new AuthFragment();
        replaceFragment(fragment, AuthFragment.TAG, true);
    }

    //region ==================== DI ========================

    @dagger.Module
    public static class Module {
        @Provides
        @RootScope
        RootPresenter provideRootPresenter() {
            return new RootPresenter();
        }
    }

    @dagger.Component(modules = Module.class)
    @RootScope
    public interface Component {
        void inject(RootActivity view);
        RootPresenter getRootPresenter();
    }

    //endregion
}
