package ru.mikhalev.vladimir.mvpauth.core.layers.presenter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.root.IRootView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Developer Vladimir Mikhalev, 05.12.2016.
 */

public abstract class SubscribePresenter<V extends ViewGroup> extends ViewPresenter<V> {
    private final String TAG = this.getClass().getSimpleName();

    @Nullable
    protected abstract IRootView getRootView();

    protected abstract class ViewSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            Log.d(TAG, "onCompleted observable");
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
