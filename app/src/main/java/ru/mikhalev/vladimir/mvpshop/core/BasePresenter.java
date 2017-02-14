package ru.mikhalev.vladimir.mvpshop.core;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpshop.features.root.IRootView;
import ru.mikhalev.vladimir.mvpshop.features.root.RootPresenter;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 27.10.2016
 */

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> extends ViewPresenter<V> {

    @Inject protected M mModel;
    @Inject protected RootPresenter mRootPresenter;

    protected CompositeSubscription mCompSubs;

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        initDagger(scope);
        Timber.tag(getClass().getSimpleName());
        Timber.d("onEnterScope: %s", scope.getName());
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        mCompSubs = new CompositeSubscription();
        initActionBar();
        initFab();
        Timber.tag(getClass().getSimpleName());
        Timber.d("onLoad");
    }

    @Override
    public void dropView(V view) {
        super.dropView(view);
        if (mCompSubs.hasSubscriptions()) {
            mCompSubs.unsubscribe();
        }
        Timber.tag(getClass().getSimpleName());
        Timber.d("dropView");
    }

    protected abstract void initDagger(MortarScope scope);

    protected abstract void initActionBar();

    protected void initFab() {
        mRootPresenter.getRootView().hideFab();
    }

    @Nullable
    protected IRootView getRootView() {
        return mRootPresenter.getRootView();
    }

    @RxLogSubscriber
    protected abstract class ViewSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            Timber.tag(getClass().getSimpleName());
            Timber.d("onComplete observable");
        }

        @Override
        public void onError(Throwable e) {
            Timber.tag(getClass().getSimpleName());
            Timber.e(e);
            if (getRootView() != null) {
                getRootView().showError(e);
            }
        }

        @Override
        public abstract void onNext(T t);
    }
}
