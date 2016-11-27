package ru.mikhalev.vladimir.mvpauth.auth;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.Provides;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AuthScope;
import ru.mikhalev.vladimir.mvpauth.flow.AbsScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;
import ru.mikhalev.vladimir.mvpauth.home.IRootView;
import ru.mikhalev.vladimir.mvpauth.home.RootActivity;
import ru.mikhalev.vladimir.mvpauth.home.RootPresenter;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

@Screen(R.layout.screen_auth)
public class AuthScreen extends AbsScreen<RootActivity.Component> {

    @Override
    public Object createScreenComponent(RootActivity.Component parentComponent) {
        return null;
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
    public interface Component {
        void inject(AuthPresenter presenter);

        void inject(AuthView view);
    }
    //endregion

    //region =============== Presenter ==============
    public class AuthPresenter extends ViewPresenter<AuthView> implements IAuthPresenter {

        @Inject
        AuthModel mAuthModel;

        @Inject
        RootPresenter mRootPresenter;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                if (checkUserAuth()) {
                    getView().showCatalogScreen();
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
            getRootView().showLoad();
            getRootView().showMessage("request for user auth");
            Handler handler = new Handler();
            handler.postDelayed(() -> getView().showCatalogScreen(), 3000);
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
                getView().showCatalogScreen();
            }
        }

        @Override
        public boolean checkUserAuth() {
            return mAuthModel.isAuthUser();
        }
    }
}


