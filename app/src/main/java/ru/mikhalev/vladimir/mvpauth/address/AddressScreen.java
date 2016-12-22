package ru.mikhalev.vladimir.mvpauth.address;

import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import flow.TreeKey;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.account.AccountModel;
import ru.mikhalev.vladimir.mvpauth.account.AccountScreen;
import ru.mikhalev.vladimir.mvpauth.data.dto.Address;
import ru.mikhalev.vladimir.mvpauth.di.scopes.AddressScope;
import ru.mikhalev.vladimir.mvpauth.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

@Screen(R.layout.screen_address)
public class AddressScreen extends BaseScreen<AccountScreen.Component> implements TreeKey {
    private AddressViewModel mViewModel;

    public AddressScreen(Address address) {
        mViewModel = new AddressViewModel(address);
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
        if (mViewModel != null) {
            return o instanceof AddressScreen && mViewModel.equals(((AddressScreen) o).mViewModel);
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        if (mViewModel != null) {
            return mViewModel.hashCode();
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
                // FIXME: 21.12.2016 this
//                getView().initView(mViewModel);
            }
        }

        @SuppressWarnings("CheckResult")
        @Override
        public void clickOnAddAddress() {
            if (getView() != null) {
                mAccountModel.updateOrInsertAddress(new Address(mViewModel));
                Flow.get(getView()).goBack();
            }
        }
    }
}
