package ru.mikhalev.vladimir.mvpauth.account;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.Provides;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.CatalogScope;
import ru.mikhalev.vladimir.mvpauth.flow.AbsScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_account)
public class AccountScreen extends AbsScreen<RootActivity.Component> {

    // FIXME: 29.11.2016 move to viewModel
    private int mCustomState = 1;

    @Override
    public Object createScreenComponent(RootActivity.Component parentComponent) {
        return null;
    }

    //region ==================== DI ========================

    @dagger.Module
    public class Module {
        @Provides
        @CatalogScope
        AccountScreen.AccountPresenter provideAccountPresenter() {
            return new AccountScreen.AccountPresenter();
        }

        @Provides
        @CatalogScope
        AccountModel provideAccountModel() {
            return new AccountModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = AccountScreen.Module.class)
    @CatalogScope
    public interface Component {
        void inject(AccountScreen.AccountPresenter presenter);

        void inject(AccountView view);
    }

    //endregion


    public class AccountPresenter extends ViewPresenter<AccountView> implements IAccountPresenter {

        @Inject AccountModel mAccountModel;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((AccountScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            if (getView() != null) {

            }
        }

        //region ==================== IAccountPresenter ========================

        @Override
        public void clickOnAddress() {

        }

        @Override
        public void switchViewState() {

        }

        @Override
        public void switchOrder(boolean isCheked) {

        }

        @Override
        public void switchPromo(boolean isCheked) {

        }

        @Override
        public void takePhoto() {

        }

        @Override
        public void chooseCamera() {

        }

        @Override
        public void chooseGallery() {

        }

        //endregion
    }
}
