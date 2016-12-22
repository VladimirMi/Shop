package ru.mikhalev.vladimir.mvpauth.root;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.account.AccountModel;
import ru.mikhalev.vladimir.mvpauth.account.AccountViewModel;
import ru.mikhalev.vladimir.mvpauth.core.App;
import ru.mikhalev.vladimir.mvpauth.core.layers.presenter.AbstractPresenter;
import ru.mikhalev.vladimir.mvpauth.data.managers.DataManager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */
public class RootPresenter extends AbstractPresenter<IRootView> implements IRootPresenter {

    private PublishSubject<ActivityResultDto> mActivityResultSubject = PublishSubject.create();
    private PublishSubject<PermissionResultDto> mPermissionResultSubject = PublishSubject.create();
    private CompositeSubscription mSubscription = new CompositeSubscription();

    @Inject AccountModel mAccountModel;

    public RootPresenter() {
        App.getRootActivityComponent().inject(this);
    }

    public PublishSubject<ActivityResultDto> getActivityResultSubject() {
        return mActivityResultSubject;
    }

    public PublishSubject<PermissionResultDto> getPermissionResultSubject() {
        return mPermissionResultSubject;
    }

    @Override
    public void dropView() {
        mSubscription.unsubscribe();
        super.dropView();
    }

    @Override
    public void initView() {
        mSubscription.add(mAccountModel.getAccountSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AccountViewModel>() {
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
                    public void onNext(AccountViewModel accountViewModel) {
                        if (getView() != null) {
                            getView().setDrawer(accountViewModel);
                        }
                    }
                }));

        mSubscription.add(Observable.interval(0, 5, TimeUnit.MINUTES)
                .observeOn(Schedulers.io())
                .doOnNext(aLong -> DataManager.getInstance().updateProductsFromNetwork())
                .subscribe());
    }

    public void checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissions, int requestCode) {

        boolean allGranted = true;
        for (String permission : permissions) {
            int grantedCode = 0;
            if (getView() != null) {
                grantedCode = ContextCompat.checkSelfPermission(((RootActivity) getView()), permission);
            }
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
        mPermissionResultSubject.onNext(new PermissionResultDto(requestCode, allGranted));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // FIXME: 10.12.2016 grantResults length may be 0
        boolean allGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                if (getView() != null) {
                    getView().showPermissionSnackbar();
                }
                allGranted = false;
                break;
            }
        }
        mPermissionResultSubject.onNext(new PermissionResultDto(requestCode, allGranted));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mActivityResultSubject.onNext(new ActivityResultDto(requestCode, resultCode, intent));
    }
}
