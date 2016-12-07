package ru.mikhalev.vladimir.mvpauth.root;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.account.AccountDto;
import ru.mikhalev.vladimir.mvpauth.account.AccountModel;
import ru.mikhalev.vladimir.mvpauth.core.App;
import ru.mikhalev.vladimir.mvpauth.core.layers.presenter.AbstractPresenter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */
public class RootPresenter extends AbstractPresenter<IRootView> implements IRootPresenter {
    private static final String TAG = "RootPresenter";
    public static final int REQUEST_CAMERA_PERMISSION = 1;
    public static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 4;

    public static final int REQUEST_CAMERA_INTENT = 3;
    public static final int REQUEST_GALLERY_INTENT = 5;
    public static final int REQUEST_SETTINGS_INTENT = 6;

    private PublishSubject<RequestPermissionsDto> mRequestPermissionsDtoObs = PublishSubject.create();
    @Inject
    AccountModel mAccountModel;

    public RootPresenter() {
        App.getRootActivityComponent().inject(this);
    }

    public PublishSubject<RequestPermissionsDto> getRequestPermissionsDtoObs() {
        return mRequestPermissionsDtoObs;
    }

    @Override
    public void initView() {
        mAccountModel.getAccountSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AccountDto>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().showError(e);
                        }
                    }

                    @Override
                    public void onNext(AccountDto accountDto) {
                        if (getView() != null) {
                            getView().setDrawer(accountDto);
                        }
                    }
                });


    }

    public void checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissions, int requestCode) {
        boolean allGranted = true;
        for (String permission : permissions) {
            int grantedCode = ContextCompat.checkSelfPermission(((RootActivity) getView()), permission);
            if (grantedCode == PackageManager.PERMISSION_DENIED) {
                allGranted = false;
                break;
            }
        }
        if (!allGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ((RootActivity) getView()).requestPermissions(permissions, requestCode);
            } else {
                ActivityCompat.requestPermissions(((RootActivity) getView()), permissions, requestCode);
            }
        }
        mRequestPermissionsDtoObs.onNext(new RequestPermissionsDto(requestCode, allGranted));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                getView().showPermissionSnackbar();
                allGranted = false;
                break;
            }
            mRequestPermissionsDtoObs.onNext(new RequestPermissionsDto(requestCode, allGranted));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mRequestPermissionsDtoObs.onNext(new RequestPermissionsDto(requestCode, resultCode, intent));
    }
}
