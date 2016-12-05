package ru.mikhalev.vladimir.mvpauth.account;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.address.AddressDto;
import ru.mikhalev.vladimir.mvpauth.address.AddressScreen;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AccountScope;
import ru.mikhalev.vladimir.mvpauth.core.layers.presenter.SubscribePresenter;
import ru.mikhalev.vladimir.mvpauth.flow.AbstractScreen;
import ru.mikhalev.vladimir.mvpauth.flow.Screen;
import ru.mikhalev.vladimir.mvpauth.root.IRootView;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;
import ru.mikhalev.vladimir.mvpauth.root.RootPresenter;
import rx.Subscription;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_account)
public class AccountScreen extends AbstractScreen<RootActivity.Component> {
    private static final String TAG = "AccountScreen";

    private int mViewState = AccountView.STATE.PREVIEW;

    public int getViewState() {
        return mViewState;
    }

    public void setViewState(@AccountView.STATE int viewState) {
        mViewState = viewState;
    }

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
        @AccountScope
        AccountScreen.AccountPresenter provideAccountPresenter() {
            return new AccountScreen.AccountPresenter();
        }

        @Provides
        @AccountScope
        AccountModel provideAccountModel() {
            return new AccountModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = AccountScreen.Module.class)
    @AccountScope
    public interface Component {
        void inject(AccountScreen.AccountPresenter presenter);

        void inject(AccountView view);

        AccountModel getAccountModel();
    }

    //endregion


    public class AccountPresenter extends SubscribePresenter<AccountView> implements IAccountPresenter {

        @Inject
        AccountModel mAccountModel;
        @Inject
        RootPresenter mRootPresenter;
        private Uri mAvatarUri;
        private Subscription mAddressSub;
        private Subscription mSettingsSub;

        @Nullable
        protected IRootView getRootView() {
            return mRootPresenter.getView();
        }

        //region =============== Lifecycle ==============

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((AccountScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView(mAccountModel.getAccountDto());
                subscribeOnAddressesObs();
            }
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            mAddressSub.unsubscribe();
        }

        @Override
        protected void onExitScope() {
            super.onExitScope();
        }

        //endregion


        //region =============== Subscription ==============

        private void subscribeOnAddressesObs() {
            mAddressSub = subscribe(mAccountModel.getAddressObs(), new ViewSubscriber<AddressDto>() {
                @Override
                public void onNext(AddressDto addressDto) {
                    if (getView() != null) {
                        getView().getAdapter().addItem(addressDto);
                    }
                }
            });
        }

        private void updateListView() {
            getView().getAdapter().reloadAdapter();
            subscribeOnAddressesObs();
        }

        private void subscribeOnSettingsObs() {
            mSettingsSub = subscribe(mAccountModel.getAccountSettingsObs(), new ViewSubscriber<AccountSettingsDto>() {
                @Override
                public void onNext(AccountSettingsDto accountSettingsDto) {
                    if (getView() != null) {

                    }
                }
            });
        }

        //endregion


        //region ==================== IAccountPresenter ========================

        @Override
        public void editAddress(int position) {
            if (getView() != null) {
                Flow.get(getView()).set(new AddressScreen(mAccountModel.getAddressFromPosition(position)));
            }
        }

        @Override
        public void clickOnAddAddress() {
            if (getView() != null) {
                Flow.get(getView()).set(new AddressScreen(null));
            }
        }

        @Override
        public void removeAddress(int position) {
            mAccountModel.removeAddress(mAccountModel.getAddressFromPosition(position));
            updateListView();
        }

        @Override
        public void switchViewState() {
            if (getViewState() == AccountView.STATE.EDIT && getView() != null) {
                mAccountModel.saveProfileInfo(getView().getUserName(), getView().getUserPhone());
                // TODO: 02.12.2016 check if avatar is changed
//                mAccountModel.saveAvatarPhoto(mAvatarUri);
            }
            getView().changeState();
        }

        @Override
        public void switchOrder(boolean isCheked) {
            mAccountModel.saveOrderNotification(isCheked);
        }

        @Override
        public void switchPromo(boolean isCheked) {
            mAccountModel.savePromoNotification(isCheked);
        }

        @Override
        public void changeAvatar() {
            if (getView() != null) {
                getView().showPhotoSourceDialog();
            }
        }

        @Override
        public void chooseCamera() {
            if (getRootView() != null) {
                String[] permissions = new String[]{CAMERA, WRITE_EXTERNAL_STORAGE};
                if (mRootPresenter.checkPermissionsAndRequestIfNotGranted(permissions,
                        mRootPresenter.REQUEST_PERMISSION_CAMERA)) {
                    mPhotoFile = createFileForPhoto();
                    if (mPhotoFile == null) {
                        getRootView().showMessage("Фотография не может быть создана");
                        return;
                    }
                    takePhotoFromCamera();
                }
            }
        }

        @Override
        public void chooseGallery() {
            if (getRootView() != null) {
                getRootView().showMessage("chooseGallery");
                // TODO: 01.12.2016 choose from gallery
            }
        }
        //endregion
    }
}
