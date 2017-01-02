package ru.mikhalev.vladimir.mvpshop.features.account;

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
import java.util.Locale;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewPresenter;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountAddressDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.ActivityResultDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.PermissionResultDto;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.AccountScope;
import ru.mikhalev.vladimir.mvpshop.features.address.AddressScreen;
import ru.mikhalev.vladimir.mvpshop.features.root.IRootView;
import ru.mikhalev.vladimir.mvpshop.features.root.RootActivity;
import ru.mikhalev.vladimir.mvpshop.features.root.RootPresenter;
import ru.mikhalev.vladimir.mvpshop.flow.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

@Screen(R.layout.screen_account)
public class AccountScreen extends BaseScreen<RootActivity.Component> {
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

        AccountModel getAccountModel();
    }

    //endregion


    public class AccountPresenter extends BaseViewPresenter<AccountView> implements IAccountPresenter {

        private static final int REQUEST_CAMERA = 100;
        private static final int REQUEST_GALLERY = 101;

        @Inject AccountModel mAccountModel;
        @Inject RootPresenter mRootPresenter;

        private CompositeSubscription mGlobalSubscriptions = new CompositeSubscription();
        private CompositeSubscription mSubscriptions = new CompositeSubscription();

        private File mPhotoFile;
        private String mCurrentAvatarPath;

        @Override
        @Nullable
        protected IRootView getRootView() {
            return mRootPresenter.getView();
        }

        //region =============== Lifecycle ==============

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            scope.<AccountScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
            mGlobalSubscriptions.add(subscribeOnActivityResultObs());
            mGlobalSubscriptions.add(subscribeOnPermissionResultObs());
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            getView().initView();
            mSubscriptions.add(subscribeOnAccountSubject());
            mSubscriptions.add(subscribeOnAddressesObs());
        }

        @Override
        protected void onExitScope() {
            super.onExitScope();
            mGlobalSubscriptions.unsubscribe();
        }

        //endregion


        //region =============== Subscription ==============

        private Subscription subscribeOnAccountSubject() {
            return mAccountModel.getAccountSubject()
                    .subscribe(new ViewSubscriber<AccountViewModel>() {
                        @Override
                        public void onNext(AccountViewModel viewModel) {
                            setupViewModel(viewModel);
                        }
                    });
        }

        private void setupViewModel(AccountViewModel accountViewModel) {
            if (getView().getViewModel() != null) {
                getView().getViewModel().update(accountViewModel);
            } else {
                mViewModel = accountViewModel;
                if (mCurrentAvatarPath != null) {
                    mViewModel.setAvatar(mCurrentAvatarPath);
                }
                getView().setViewModel(mViewModel);
            }
        }

        private Subscription subscribeOnAddressesObs() {
            return mAccountModel.getAccountAddresses()
                    .subscribe(new ViewSubscriber<AccountAddressDto>() {
                        @Override
                        public void onNext(AccountAddressDto accountAddressDto) {
                            getView().getAdapter().addItem(accountAddressDto);
                        }
                    });
        }

        private Subscription subscribeOnPermissionResultObs() {
            Observable<PermissionResultDto> permissionResultObservable = mRootPresenter.getPermissionResultSubject()
                    .filter(PermissionResultDto::isGranted);

            return permissionResultObservable.subscribe(new ViewSubscriber<PermissionResultDto>() {
                @Override
                public void onNext(PermissionResultDto permissionResultDto) {
                    handlePermissionResult(permissionResultDto);
                }
            });
        }

        private Subscription subscribeOnActivityResultObs() {
            Observable<ActivityResultDto> activityResultObservable = mRootPresenter.getActivityResultSubject()
                    .filter(activityResultDto -> activityResultDto.getResultCode() == Activity.RESULT_OK);
            return activityResultObservable.subscribe(new ViewSubscriber<ActivityResultDto>() {
                @Override
                public void onNext(ActivityResultDto activityResultDto) {
                    handleActivityResult(activityResultDto);
                }
            });
        }

        //endregion

        //region ==================== IAccountPresenter ========================

        @Override
        public void editAddress(int position) {
            if (getView() != null) {
                AccountAddressDto addressDto = mAccountModel.getAddressFromPosition(position);
                Flow.get(getView()).set(new AddressScreen(addressDto));
            }
        }

        @Override
        public void clickOnAddAddress() {
            if (getView() != null) {
                // TODO: 13.12.2016 generate new id
                int id = 777;
                Flow.get(getView()).set(new AddressScreen(new AccountAddressDto(id)));
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
            if (mViewModel.getViewState() == AccountViewModel.STATE.EDIT) {
                mAccountModel.saveAccountProfile(mViewModel);
            }
            getView().changeState();
        }

        @Override
        public void switchNotification() {
            mAccountModel.saveAccountSetting(mViewModel);
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
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
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
            Timber.e("takePhotoFromCamera: ");
            mPhotoFile = createFileForPhoto();
            if (mPhotoFile == null) {
                getRootView().showMessage("Фотография не может быть создана");
                return;
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoURI = FileProvider.getUriForFile(((RootActivity) getRootView()),
                    "ru.mikhalev.vladimir.mvpshop.provider",
                    mPhotoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            ((RootActivity) getRootView()).startActivityForResult(intent, REQUEST_CAMERA);
        }

        //endregion

        //region ==================== Gallery ========================

        @Override
        public void chooseGallery() {
            String[] permissions = new String[]{READ_EXTERNAL_STORAGE};
            mRootPresenter.checkPermissionsAndRequestIfNotGranted(permissions, REQUEST_GALLERY);
        }

        private void takePhotoFromGallery() {
            Timber.e("takePhotoFromGallery: ");
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

        private void handlePermissionResult(PermissionResultDto permissionResultDto) {
            switch (permissionResultDto.getRequestCode()) {
                case REQUEST_CAMERA:
                    takePhotoFromCamera();
                    break;
                case REQUEST_GALLERY:
                    takePhotoFromGallery();
                    break;
            }
        }

        private void handleActivityResult(ActivityResultDto activityResultDto) {
            Observable<String> resultObs = Observable.just(activityResultDto)
                    .map(dto -> {
                        switch (dto.getRequestCode()) {
                            case REQUEST_CAMERA:
                                return mPhotoFile.getAbsolutePath();
                            case REQUEST_GALLERY:
                                return dto.getIntent().getData().toString();
                            default:
                                return null;
                        }
                    });
            resultObs.subscribe(new ViewSubscriber<String>() {
                @Override
                public void onNext(String s) {
                    mCurrentAvatarPath = s;
                    if (mViewModel != null) {
                        mViewModel.setAvatar(mCurrentAvatarPath);
                    }
                }
            });
        }
    }
}