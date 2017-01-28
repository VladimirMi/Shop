package ru.mikhalev.vladimir.mvpshop.features.root;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import mortar.Presenter;
import mortar.bundler.BundleService;
import ru.mikhalev.vladimir.mvpshop.core.App;
import ru.mikhalev.vladimir.mvpshop.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountModel;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountViewModel;
import ru.mikhalev.vladimir.mvpshop.utils.AppConfig;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */
public class RootPresenter extends Presenter<IRootView> implements IRootPresenter {

    public static final int REQUEST_CAMERA = 100;
    public static final int REQUEST_GALLERY = 101;

    private BehaviorSubject<String> mPhotoPathSubject = BehaviorSubject.create();
    private String mCurrentPhotoPath;

    private CompositeSubscription mCompSubs = new CompositeSubscription();

    @Inject AccountModel mAccountModel;

    public RootPresenter() {
        App.getRootActivityComponent().inject(this);
    }

    @Override
    protected BundleService extractBundleService(IRootView view) {
        return BundleService.getBundleService((Context) view);
    }

    public BehaviorSubject<String> getPhotoPathSubject() {
        return mPhotoPathSubject;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        mCompSubs.add(subscribeOnAccount());
        mCompSubs.add(subscribeOnProductsTimer());
    }

    @Override
    public void dropView(IRootView view) {
        super.dropView(view);
        mCompSubs.unsubscribe();
    }

    private Subscription subscribeOnAccount() {

        return mAccountModel.getAccountObs()
                .map(AccountViewModel::new)
                .subscribe(getView()::setDrawer, getView()::showError);
    }

    private Subscription subscribeOnProductsTimer() {
        // TODO: 04.01.2017 inject datamanger?
        return Observable.interval(0, 5, TimeUnit.MINUTES)
                .observeOn(Schedulers.io())
                .subscribe(aLong -> DataManager.getInstance().updateProductsFromNetwork());
    }

    @Nullable
    public IRootView getRootView() {
        return getView();
    }

    public void resolvePermissions(@NonNull String[] permissions, int requestCode) {

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
        } else {
            resolveGrantedPermissions(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length == 0) {
            return;
        }
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                if (getView() != null) {
                    getView().showPermissionSnackBar();
                }
                return;
            }
        }
        resolveGrantedPermissions(requestCode);
    }

    private void resolveGrantedPermissions(int requestCode) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                takePhotoFromCamera();
                break;
            case REQUEST_GALLERY:
                takePhotoFromGallery();
                break;
        }
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
        ((RootActivity) getView()).startActivityForResult(intent, REQUEST_GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoURI = FileProvider.getUriForFile(((RootActivity) getView()),
                AppConfig.FILE_PROVIDER_AUTHORITY,
                createFileForPhoto());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        ((RootActivity) getView()).startActivityForResult(intent, REQUEST_CAMERA);
    }

    private
    @Nullable
    File createFileForPhoto() {
        String timeStamp = SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date());
        String imageFileName = AppConfig.PHOTO_FILE_NAME_PREFIX + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image;
        try {
            image = File.createTempFile(imageFileName, AppConfig.PHOTO_FILE_NAME_SUFFIX, storageDir);
        } catch (IOException e) {
            getView().showError(e);
            return null;
        }
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY) {
                if (intent != null) {
                    mCurrentPhotoPath = intent.getData().toString();
                }
            }
            mPhotoPathSubject.onNext(mCurrentPhotoPath);
        }
    }

    public ActionBarBuilder newActionBarBuilder() {
        return this.new ActionBarBuilder();
    }

    public class ActionBarBuilder {
        private static final int DEFAULT_MODE = 0;
        private static final int TAB_MODE = 1;

        // TODO: 20.01.2017 setBackArrow and lock drawer split
        private boolean isGoBack = false;
        private boolean isVisible = true;
        private String title;
        private List<MenuItemHolder> items = new ArrayList<>();
        private ViewPager pager;
        private int toolbarMode = DEFAULT_MODE;

        public ActionBarBuilder setBackArrow(boolean enable) {
            this.isGoBack = enable;
            return this;
        }

        public ActionBarBuilder setVisible(boolean enable) {
            isVisible = enable;
            return this;
        }

        public ActionBarBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ActionBarBuilder addAction(MenuItemHolder item) {
            this.items.add(item);
            return this;
        }

        public ActionBarBuilder setTab(ViewPager pager) {
            this.toolbarMode = TAB_MODE;
            this.pager = pager;
            return this;
        }

        public void build() {
            if (getView() != null) {
                RootActivity activity = (RootActivity) getView();
                activity.setBackArrow(isGoBack);
                activity.setToolbarVisible(isVisible);
                activity.setTitle(title);
                activity.setMenuItems(items);
                if (toolbarMode == TAB_MODE) {
                    activity.setTabLayout(pager);
                } else {
                    activity.removeTabLayout();
                }
            }
        }
    }
}
