package ru.mikhalev.vladimir.mvpshop.features.account;

import android.os.Bundle;

import dagger.Provides;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.root.MenuItemHolder;
import ru.mikhalev.vladimir.mvpshop.features.root.RootActivity;
import ru.mikhalev.vladimir.mvpshop.features.root.RootPresenter;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;
import rx.Subscription;
import timber.log.Timber;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static ru.mikhalev.vladimir.mvpshop.features.root.RootPresenter.REQUEST_CAMERA;
import static ru.mikhalev.vladimir.mvpshop.features.root.RootPresenter.REQUEST_GALLERY;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_account)
public class
AccountScreen extends BaseScreen<RootActivity.Component> {
    private AccountViewModel mViewModel;

    @Override
    public Object createScreenComponent(RootActivity.Component parentComponent) {
        return DaggerAccountScreen_Component.builder()
                .component(parentComponent)
                .module(new AccountScreen.Module())
                .build();
    }

    //region ==================== DI ========================

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(AccountScreen.class)
        AccountScreen.AccountPresenter provideAccountPresenter() {
            return new AccountScreen.AccountPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = AccountScreen.Module.class)
    @DaggerScope(AccountScreen.class)
    public interface Component {
        void inject(AccountScreen.AccountPresenter presenter);

        void inject(AccountView view);

        AccountModel getAccountModel();

        RootPresenter getRootPresenter();
    }

    //endregion


    public class AccountPresenter extends BasePresenter<AccountView, AccountModel> implements IAccountPresenter {

        private String mCurrentAvatarPath;
        private AccountRealm mAccountRealm;


        //region =============== Lifecycle ==============

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            mCompSubs.add(subscribeOnAccountObs());
            mCompSubs.add(subscribeOnPhotoPathObs());
        }

        @Override
        protected void initDagger(MortarScope scope) {
            scope.<AccountScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .addActoin(new MenuItemHolder("В корзину", R.drawable.ic_shopping_cart_color_primary_dark_24dp,
                            item -> {
                                getRootView().showMessage("Перейти в корзину");
                                return true;
                            }))
                    .build();
        }

        //endregion


        //region =============== Subscription ==============

        private Subscription subscribeOnAccountObs() {
            Timber.e("subscribeOnAccountObs: ");
            return mModel.getAccountObs()
                    .subscribe(new ViewSubscriber<AccountRealm>() {
                        @Override
                        public void onNext(AccountRealm accountRealm) {
                            mAccountRealm = accountRealm;
                            if (mCurrentAvatarPath != null) {
                                mAccountRealm.setAvatar(mCurrentAvatarPath);
                            }
                            getView().setViewModel(mAccountRealm);
                        }
                    });
        }


        private Subscription subscribeOnPhotoPathObs() {
            return mRootPresenter.getPhotoPathSubject()
                    .subscribe(new ViewSubscriber<String>() {
                        @Override
                        public void onNext(String path) {
                            mCurrentAvatarPath = path;
                            if (mAccountRealm != null) {
                                mAccountRealm.setAvatar(path);
                            }
                        }
                    });
        }

        //endregion

        //region ==================== IAccountPresenter ========================

        @Override
        public void saveAccount(AccountRealm accountRealm) {
            mModel.saveAccount(accountRealm);
        }

        @Override
        public void removeAddress(AddressRealm addressRealm) {
            mModel.removeAddress(addressRealm);
        }

        @Override
        public void chooseCamera() {
            String[] permissions = new String[]{CAMERA, WRITE_EXTERNAL_STORAGE};
            mRootPresenter.resolvePermissions(permissions, REQUEST_CAMERA);
        }

        @Override
        public void chooseGallery() {
            String[] permissions = new String[]{READ_EXTERNAL_STORAGE};
            mRootPresenter.resolvePermissions(permissions, REQUEST_GALLERY);
        }
    }
}
