package ru.mikhalev.vladimir.mvpshop.core;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpshop.features.root.IRootView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 05.12.2016.
 */

public abstract class SubscribePresenter<V extends ViewGroup> extends ViewPresenter<V> {

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void dropView(V view) {
        super.dropView(view);
        mSubscriptions.unsubscribe();
    }

    @Nullable
    protected abstract IRootView getRootView();

    protected abstract class ViewSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            Timber.d("onCompleted");
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

    protected <T> Subscription subscribe(Observable<T> observable, ViewSubscriber<T> subscriber) {
        return observable
                .subscribe(subscriber);
    }
}
