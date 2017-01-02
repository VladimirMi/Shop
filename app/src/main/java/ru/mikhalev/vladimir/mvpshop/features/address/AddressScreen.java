package ru.mikhalev.vladimir.mvpshop.features.address;

import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import flow.TreeKey;
import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountAddressDto;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.AddressScope;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountModel;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountScreen;
import ru.mikhalev.vladimir.mvpshop.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

@Screen(R.layout.screen_address)
public class AddressScreen extends BaseScreen<AccountScreen.Component> implements TreeKey {
    private AddressViewModel mViewModel;

    public AddressScreen(AccountAddressDto address) {
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
        void inject(AddressPresenter presenter);

        void inject(AddressView view);
    }

    //endregion

    public class AddressPresenter extends ViewPresenter<AddressView> implements IAddressPresenter {

        @Inject
        AccountModel mAccountModel;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            scope.<AddressScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            getView().initView();
            getView().setViewModel(mViewModel);
        }

        @SuppressWarnings("CheckResult")
        @Override
        public void clickOnAddAddress() {
            if (getView() != null) {
                mAccountModel.updateOrInsertAddress(new AccountAddressDto(mViewModel));
                Flow.get(getView()).goBack();
            }
        }
    }
}
