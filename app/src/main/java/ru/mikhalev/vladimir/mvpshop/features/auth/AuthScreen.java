package ru.mikhalev.vladimir.mvpshop.features.auth;

import android.os.Bundle;
import android.os.Handler;

import dagger.Provides;
import flow.Direction;
import flow.Flow;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpshop.features.root.RootActivity;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;

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
        @DaggerScope(AuthScreen.class)
        AuthPresenter provideAuthPresenter() {
            return new AuthPresenter();
        }

        @Provides
        @DaggerScope(AuthScreen.class)
        AuthModel provideAuthModel() {
            return new AuthModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = Module.class)
    @DaggerScope(AuthScreen.class)
    public interface Component {
        void inject(AuthPresenter presenter);

        void inject(AuthView view);
    }
    //endregion


    class AuthPresenter extends BasePresenter<AuthView, AuthModel> implements IAuthPresenter {

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
            getView().setViewModel(mViewModel);
        }

        @Override
        protected void initDagger(MortarScope scope) {
            scope.<AuthScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            // TODO: 04.01.2017 implement this
        }

        @Override
        public void clickOnLogin() {
            mModel.loginUser(mViewModel.getEmail(), mViewModel.getPassword());

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
                Flow.get(getView()).replaceTop(new CatalogScreen(), Direction.REPLACE);
            }
        }

        @Override
        public boolean checkUserAuth() {
            return false;
        }
    }
}


