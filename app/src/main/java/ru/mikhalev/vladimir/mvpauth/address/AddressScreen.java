package ru.mikhalev.vladimir.mvpauth.address;

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
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AddressScope;
import ru.mikhalev.vladimir.mvpauth.flow.AbstractScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

@Screen(R.layout.screen_address)
public class AddressScreen extends AbstractScreen<AccountScreen.Component> implements TreeKey {
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
            ((AddressScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        public void clickOnAddAddress() {
            // TODO: 04.12.2016 save address in model
            if (getView() != null) {
                mAccountModel.addAddress(getView().getUserAddress());
                boolean goBack = Flow.get(getView()).goBack();
            }
        }
    }
}
