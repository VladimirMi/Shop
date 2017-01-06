package ru.mikhalev.vladimir.mvpshop.features.root;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import mortar.Presenter;
import mortar.bundler.BundleService;
import ru.mikhalev.vladimir.mvpshop.core.App;
import ru.mikhalev.vladimir.mvpshop.data.dto.ActivityResultDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.PermissionResultDto;
import ru.mikhalev.vladimir.mvpshop.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountModel;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountViewModel;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */
public class RootPresenter extends Presenter<IRootView> implements IRootPresenter {

    private static int DEFAULT_MODE = 0;
    private static int TAB_MODE = 1;

    private PublishSubject<ActivityResultDto> mActivityResultSubject = PublishSubject.create();
    private PublishSubject<PermissionResultDto> mPermissionResultSubject = PublishSubject.create();
    private CompositeSubscription mCompSubs = new CompositeSubscription();

    @Inject AccountModel mAccountModel;

    public RootPresenter() {
        App.getRootActivityComponent().inject(this);
    }

    @Override
    protected BundleService extractBundleService(IRootView view) {
        return BundleService.getBundleService((Context) view);
    }

    public PublishSubject<ActivityResultDto> getActivityResultSubject() {
        return mActivityResultSubject;
    }

    public PublishSubject<PermissionResultDto> getPermissionResultSubject() {
        return mPermissionResultSubject;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        mCompSubs.add(subscribeOnAccountInfo());
        mCompSubs.add(subscribeOnProductsTimer());
    }

    @Override
    public void dropView(IRootView view) {
        super.dropView(view);
        mCompSubs.unsubscribe();
    }

    private Subscription subscribeOnAccountInfo() {
        return mAccountModel.getAccountSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UserInfoSubscriber());
    }

    private Subscription subscribeOnProductsTimer() {
        // TODO: 04.01.2017 inject datamanger?
        return Observable.interval(0, 5, TimeUnit.MINUTES)
                .observeOn(Schedulers.io())
                .doOnNext(aLong -> DataManager.getInstance().updateProductsFromNetwork())
                .subscribe();
    }

    @Nullable
    public IRootView getRootView() {
        return getView();
    }

    public ActionBarBuilder newActionBarBuilder() {
        return this.new ActionBarBuilder();
    }

    @RxLogSubscriber
    private class UserInfoSubscriber extends Subscriber<AccountViewModel> {
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
                    getView().showPermissionSnackBar();
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

    public class ActionBarBuilder {
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

        public ActionBarBuilder addActoin(MenuItemHolder item) {
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
