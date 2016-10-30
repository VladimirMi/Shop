package ru.mikhalev.vladimir.mvpauth;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.ImageView;

import ru.mikhalev.vladimir.mvpauth.account.AccountFragment;
import ru.mikhalev.vladimir.mvpauth.auth.AuthFragment;
import ru.mikhalev.vladimir.mvpauth.catalog.CatalogFragment;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseActivity;
import ru.mikhalev.vladimir.mvpauth.core.base.view.IView;
import ru.mikhalev.vladimir.mvpauth.core.utils.MyGlideModule;
import ru.mikhalev.vladimir.mvpauth.databinding.ActivityRootBinding;

public class RootActivity extends BaseActivity implements IView, NavigationView.OnNavigationItemSelectedListener {
    private ActivityRootBinding mBinding;
    private FragmentManager mFragmentManager;
    private boolean isExitEnabled = false;

    //region ==================== Life cycle ========================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_root);

        initToolbar();
        initDrawer();

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            replaceFragment(new CatalogFragment(), CatalogFragment.TAG);
        }
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
        MyGlideModule.setUserAvatar(this, R.drawable.user_avatar, avatar);
    }

    @Override
    public void onBackPressed() {
        CatalogFragment fragment = (CatalogFragment) mFragmentManager.findFragmentByTag(CatalogFragment.TAG);

        if (mBinding.drawer.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawer.closeDrawer(GravityCompat.START);
        } else if (fragment == null) {
            replaceFragment(new CatalogFragment(), CatalogFragment.TAG);
            mBinding.navigationView.setCheckedItem(R.id.nav_catalog);
        } else if (isExitEnabled) {
            super.onBackPressed();
        } else {
            showMessage(getString(R.string.message_exit));
            isExitEnabled = true;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_accout:
                if (!item.isChecked()) {
                    replaceFragment(new AccountFragment(), AccountFragment.TAG);
                }
                break;

            case R.id.nav_catalog:
                if (!item.isChecked()) {
                    replaceFragment(new CatalogFragment(), CatalogFragment.TAG);
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

    private void replaceFragment(Fragment fragment, String tag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
        isExitEnabled = false;
    }

    public void showAuthFragment() {
        AuthFragment fragment = new AuthFragment();
        replaceFragment(fragment, AuthFragment.TAG);
    }
}
