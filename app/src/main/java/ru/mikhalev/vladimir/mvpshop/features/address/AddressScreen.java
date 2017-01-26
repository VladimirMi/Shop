package ru.mikhalev.vladimir.mvpshop.features.address;

import android.os.Bundle;
import android.support.annotation.NonNull;

import dagger.Provides;
import flow.Flow;
import flow.TreeKey;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountModel;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

@Screen(R.layout.screen_address)
public class AddressScreen extends BaseScreen<AccountScreen.Component> implements TreeKey {
    private AddressViewModel mViewModel;

    public AddressScreen(AddressRealm addressRealm) {
        mViewModel = new AddressViewModel(addressRealm);
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
        @DaggerScope(AddressScreen.class)
        AddressPresenter provideAddressPresenter() {
            return new AddressPresenter();
        }
    }

    @dagger.Component(dependencies = AccountScreen.Component.class,
            modules = AddressScreen.Module.class)
    @DaggerScope(AddressScreen.class)
    public interface Component {
        void inject(AddressPresenter presenter);

        void inject(AddressView view);
    }

    //endregion

    public class AddressPresenter extends BasePresenter<AddressView, AccountModel> implements IAddressPresenter {

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            // TODO: 04.01.2017 move to base
            getView().initView();
            getView().setViewModel(mViewModel);
        }

        @Override
        protected void initDagger(MortarScope scope) {
            scope.<AddressScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            // TODO: 04.01.2017 init this
        }


        //region =============== IAddressPresenter ==============

        @SuppressWarnings("CheckResult")
        @Override
        public void clickOnAddAddress() {
            if (getView() != null) {
                // FIXME: 26.01.2017 this
//                mModel.updateOrInsertAddress(new AccountAddressDto(mViewModel));
                Flow.get(getView()).goBack();
            }
        }

        //endregion
    }
}
