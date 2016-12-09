package ru.mikhalev.vladimir.mvpauth.auth;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AuthScope;
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

    private int mInputState = AuthView.STATE.IDLE;

    public int getInputState() {
        return mInputState;
    }

    public void setInputState(@AuthView.STATE int inputState) {
        this.mInputState = inputState;
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
        AuthViewModel provideAuthViewModel() {
            return new AuthViewModel();
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

    class AuthPresenter extends ViewPresenter<AuthView> implements IAuthPresenter {

        @Inject AuthModel mAuthModel;
        @Inject RootPresenter mRootPresenter;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((AuthScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                if (checkUserAuth()) {
                    clickOnShowCatalog();
                    return;
                }
                if (getRootView() != null) {
                    getRootView().hideToolbar();
                    getRootView().lockDrawer();
                }
            }
        }

        @Nullable
        private IRootView getRootView() {
            return mRootPresenter.getView();
        }

        @Override
        public void clickOnLogin(AuthViewModel viewModel) {
            mAuthModel.loginUser(viewModel.getEmail(), viewModel.getPassword());

            // TODO: 30.10.2016 Registration stab
            if (getRootView() != null) {
                getRootView().showLoad();
                getRootView().showMessage("request for user auth");
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
                // TODO: 27-Oct-16 if update data complete start catalog screen
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
            return mAuthModel.isAuthUser();
        }
    }
}


