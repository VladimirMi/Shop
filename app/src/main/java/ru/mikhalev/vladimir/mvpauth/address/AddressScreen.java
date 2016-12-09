package ru.mikhalev.vladimir.mvpauth.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import flow.TreeKey;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.account.AccountModel;
import ru.mikhalev.vladimir.mvpauth.account.AccountScreen;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AddressScope;
import ru.mikhalev.vladimir.mvpauth.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

@Screen(R.layout.screen_address)
public class AddressScreen extends BaseScreen<AccountScreen.Component> implements TreeKey {
    @Nullable
    private AddressViewModel mAddressViewModel;

    public AddressScreen(@Nullable AddressViewModel addressViewModel) {
        mAddressViewModel = addressViewModel;
    }

    @Override
    public Object createScreenComponent(AccountScreen.Component parentComponent) {
        return DaggerAddressScreen_Component.builder()
                .component(parentComponent)
                .module(new AddressScreen.Module())
                .build();
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new AccountScreen();
    }

    @Override
    public boolean equals(Object o) {
        if (mAddressViewModel != null) {
            return o instanceof AddressScreen && mAddressViewModel.equals(((AddressScreen) o).mAddressViewModel);
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        if (mAddressViewModel != null) {
            return mAddressViewModel.hashCode();
        } else {
            return super.hashCode();
        }
    }

    //region =============== DI ==============

    @dagger.Module
    public class Module {
        @Provides
        @AddressScope
        AddressPresenter provideAddressPresenter() {
            return new AddressPresenter();
        }
    }

    @dagger.Component(dependencies = AccountScreen.Component.class,
            modules = AddressScreen.Module.class)
    @AddressScope
    public interface Component {
//        void inject(AddressPresenter presenter);

        void inject(AddressView view);
    }

    //endregion

    public class AddressPresenter extends ViewPresenter<AddressView> implements IAddressPresenter {

        @Inject
        AccountModel mAccountModel;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
//            ((AddressScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView(mAddressViewModel);
            }
        }

        @Override
        public void clickOnAddAddress() {
            if (getView() != null) {
                mAccountModel.updateOrInsertAddress(getView().getUserAddress());
                Flow.get(getView()).goBack();
            }
        }
    }
}
