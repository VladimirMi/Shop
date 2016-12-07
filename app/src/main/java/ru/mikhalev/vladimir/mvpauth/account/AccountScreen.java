package ru.mikhalev.vladimir.mvpauth.account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import ru.mikhalev.vladimir.mvpauth.root.RequestPermissionsDto;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;
import ru.mikhalev.vladimir.mvpauth.root.RootPresenter;
import rx.Observable;
import rx.Subscription;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
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
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = AccountScreen.Module.class)
    @AccountScope
    public interface Component {
        void inject(AccountScreen.AccountPresenter presenter);

        void inject(AccountView view);
    }

    //endregion


    public class AccountPresenter extends SubscribePresenter<AccountView> implements IAccountPresenter {

        private static final int REQUEST_CAMERA = 100;
        private static final int REQUEST_GALLERY = 101;
        @Inject
        AccountModel mAccountModel;
        @Inject
        RootPresenter mRootPresenter;
        private Subscription mAddressSub;
        private Subscription mAccountSub;
        private Subscription mActivityResultSub;
        private File mPhotoFile;
        private AccountDto mViewModel;
        private String mCurrentPhotoPath;

        @Override
        @Nullable
        protected IRootView getRootView() {
            return mRootPresenter.getView();
        }

        //region =============== Lifecycle ==============

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((AccountScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
            subscribeOnActivityResultObs();
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView();
                subscribeOnAccountSubject();
                subscribeOnAddressesObs();
            }
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            mAccountSub.unsubscribe();
            mAddressSub.unsubscribe();
            mAccountModel.saveAccount(mViewModel);
        }

        @Override
        protected void onExitScope() {
            super.onExitScope();
            mActivityResultSub.unsubscribe();
        }

        //endregion


        //region =============== Subscription ==============
        private void subscribeOnAccountSubject() {
            mAccountSub = subscribe(mAccountModel.getAccountSubject(), new ViewSubscriber<AccountDto>() {
                @Override
                public void onNext(AccountDto accountDto) {
                    mViewModel = mAccountModel.getAccountDto();
                    if (mCurrentPhotoPath != null) {
                        mViewModel.setAvatar(mCurrentPhotoPath);
                    }
                    getView().setViewModel(mViewModel);
                }
            });
        }


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

        private void subscribeOnActivityResultObs() {
            Observable<RequestPermissionsDto> requestPermissionsObs = mRootPresenter.getRequestPermissionsDtoObs();

            requestPermissionsObs
                    .filter(RequestPermissionsDto::isGranted)
                    .filter(requestPermissionsDto -> requestPermissionsDto.getRequestCode() == REQUEST_CAMERA)
                    .subscribe(requestPermissionsDto -> takePhotoFromCamera(), getRootView()::showError);

            requestPermissionsObs
                    .filter(RequestPermissionsDto::isGranted)
                    .filter(requestPermissionsDto -> requestPermissionsDto.getRequestCode() == REQUEST_GALLERY)
                    .subscribe(requestPermissionsDto -> takePhotoFromGallery(), getRootView()::showError);

            Observable<String> resultObs = requestPermissionsObs
                    .filter(requestPermissionsDto -> requestPermissionsDto.getResultCode() == Activity.RESULT_OK)
                    .map(requestPermissionsDto -> {
                        switch (requestPermissionsDto.getRequestCode()) {
                            case REQUEST_CAMERA:
                                return mPhotoFile.getAbsolutePath();
                            case REQUEST_GALLERY:
                                return requestPermissionsDto.getIntent().getData().toString();
                            default:
                                return null;
                        }
                    });

            mActivityResultSub = subscribe(resultObs, new ViewSubscriber<String>() {
                @Override
                public void onNext(String result) {
                    mCurrentPhotoPath = result;
                    if (mViewModel != null) {
                        mViewModel.setAvatar(mCurrentPhotoPath);
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

        private void updateListView() {
            getView().getAdapter().reloadAdapter();
            subscribeOnAddressesObs();
        }

        @Override
        public void switchViewState() {
            if (getViewState() == AccountView.STATE.EDIT) {
                mAccountModel.saveAccount(mViewModel);
            }
            getView().changeState();
        }

        @Override
        public void switchNotification() {
            mAccountModel.saveAccount(mViewModel);
        }

        @Override
        public void changeAvatar() {
            if (getView() != null) {
                getView().showPhotoSourceDialog();
            }
        }

        //region ==================== Camera ========================

        @Override
        public void chooseCamera() {
            String[] permissions = new String[]{CAMERA, WRITE_EXTERNAL_STORAGE};
            mRootPresenter.checkPermissionsAndRequestIfNotGranted(permissions, REQUEST_CAMERA);
        }

        private File createFileForPhoto() {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "IMG_" + timeStamp;
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile;
            try {
                imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            } catch (IOException e) {
                return null;
            }
            return imageFile;
        }

        private void takePhotoFromCamera() {
            mPhotoFile = createFileForPhoto();
            if (mPhotoFile == null) {
                getRootView().showMessage("Фотография не может быть создана");
                return;
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(((RootActivity) getRootView()).getPackageManager()) != null) {
                Uri photoURI = FileProvider.getUriForFile(((RootActivity) getRootView()),
                        "ru.mikhalev.vladimir.mvpauth.provider",
                        mPhotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                ((RootActivity) getRootView()).startActivityForResult(intent, REQUEST_CAMERA);
            }
        }

        //endregion

        //region ==================== Gallery ========================

        @Override
        public void chooseGallery() {
            String[] permissions = new String[]{READ_EXTERNAL_STORAGE};
            mRootPresenter.checkPermissionsAndRequestIfNotGranted(permissions, REQUEST_GALLERY);
        }

        private void takePhotoFromGallery() {
            Intent intent = new Intent();
            intent.setType("image/*");
            if (Build.VERSION.SDK_INT < 19) {
                intent.setAction(Intent.ACTION_GET_CONTENT);
            } else {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
            }
            ((RootActivity) getRootView()).startActivityForResult(intent, REQUEST_GALLERY);
        }

        //endregion

        //endregion

    }
}
