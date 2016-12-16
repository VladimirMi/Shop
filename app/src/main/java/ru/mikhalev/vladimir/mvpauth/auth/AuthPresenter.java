package ru.mikhalev.vladimir.mvpauth.auth;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import flow.Flow;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpauth.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.layers.presenter.SubscribePresenter;
import ru.mikhalev.vladimir.mvpauth.root.IRootView;
import ru.mikhalev.vladimir.mvpauth.root.RootPresenter;

/**
 * Developer Vladimir Mikhalev 09.12.2016
 */

class AuthPresenter extends SubscribePresenter<AuthView> implements IAuthPresenter {

    @Inject
    AuthModel mAuthModel;
    @Inject
    RootPresenter mRootPresenter;
    private AuthViewModel mViewModel;

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        scope.<AuthScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        if (checkUserAuth()) {
            clickOnShowCatalog();
            return;
        }
        if (getRootView() != null) {
            getRootView().hideToolbar();
            getRootView().lockDrawer();
        }
        mViewModel = getView().getViewModel();
        getView().initView();
    }

    @Override
    @Nullable
    protected IRootView getRootView() {
        return mRootPresenter.getView();
    }

    @Override
    public void clickOnLogin() {
        mAuthModel.loginUser(mViewModel.getEmail(), mViewModel.getPassword());

        if (getRootView() != null) {
            getRootView().showLoad();
        }
        Handler handler = new Handler();
        handler.postDelayed(this::clickOnShowCatalog, 3000);
    }

    @Override
    public void clickOnFb() {
        if (getView() != null) {
            // TODO: 30.10.2016 fb logic
        }
    }

    @Override
    public void clickOnVk() {
        if (getView() != null) {
            // TODO: 30.10.2016 vk logic
        }
    }

    @Override
    public void clickOnTwitter() {
        if (getView() != null) {
            // TODO: 30.10.2016 tw logic
        }
    }

    @Override
    public void clickOnShowCatalog() {
        if (getView() != null) {
            if (getRootView() != null) {
                getRootView().hideLoad();
                getRootView().showToolbar();
                getRootView().unlockDrawer();
            }
            Flow.get(getView()).set(new CatalogScreen());
        }
    }

    @Override
    public boolean checkUserAuth() {
        return false;
    }
}
