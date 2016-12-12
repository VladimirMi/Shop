package ru.mikhalev.vladimir.mvpauth.auth;

import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AuthScope;
import ru.mikhalev.vladimir.mvpauth.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;

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
}


