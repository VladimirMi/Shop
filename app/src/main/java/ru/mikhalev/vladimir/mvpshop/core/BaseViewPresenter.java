package ru.mikhalev.vladimir.mvpshop.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpshop.features.root.IRootView;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 05.12.2016.
 */

public abstract class BaseViewPresenter<V extends ViewGroup> extends ViewPresenter<V> {

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    protected void onEnterScope(MortarScope scope) {
        Timber.tag(getClass().getSimpleName());
        Timber.d("onEnterScope: ");
        super.onEnterScope(scope);
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        Timber.tag(getClass().getSimpleName());
        Timber.d("onLoad: ");
        super.onLoad(savedInstanceState);
    }

    @Override
    public void dropView(V view) {
        Timber.tag(getClass().getSimpleName());
        Timber.d("dropView: ");
        super.dropView(view);
        mSubscriptions.unsubscribe();
    }


    @Nullable
    protected abstract IRootView getRootView();

    protected abstract class ViewSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            Timber.tag(getClass().getSimpleName());
            Timber.d("onCompleted: ");
        }

        @Override
        public void onError(Throwable e) {
            if (getRootView() != null) {
                getRootView().showError(e);
            }
        }

        @Override
        public abstract void onNext(T t);
    }
}
