package ru.mikhalev.vladimir.mvpauth.auth;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpauth.core.base.SubscribePresenter;
import ru.mikhalev.vladimir.mvpauth.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.di.scopes.AuthScope;
import ru.mikhalev.vladimir.mvpauth.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;
import ru.mikhalev.vladimir.mvpauth.root.IRootView;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;
import ru.mikhalev.vladimir.mvpauth.root.RootPresenter;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

@Screen(R.layout.screen_auth)
public class AuthScreen extends BaseScreen<RootActivity.Component> {
    private AuthViewModel mViewModel = new AuthViewModel();

    public AuthViewModel getViewModel() {
        return mViewModel;
    }

    public void setViewModel(AuthViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public Object createScreenComponent(RootActivity.Component parentComponent) {
        return DaggerAuthScreen_Component.builder()
                .component(parentComponent)
                .module(new AuthScreen.Module())
                .build();
    }

    //region =============== DI ==============
    @dagger.Module
    public class Module {
        @Provides
        @AuthScope
        AuthPresenter provideAuthPresenter() {
            return new AuthPresenter();
        }

        @Provides
        @AuthScope
        AuthModel provideAuthModel() {
            return new AuthModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = Module.class)
    @AuthScope
    public interface Component {
        void inject(AuthPresenter presenter);

        void inject(AuthView view);
    }
    //endregion


    class AuthPresenter extends SubscribePresenter<AuthView> implements IAuthPresenter {

        @Inject
        AuthModel mAuthModel;
        @Inject
        RootPresenter mRootPresenter;

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
            return true;
        }
    }
}


