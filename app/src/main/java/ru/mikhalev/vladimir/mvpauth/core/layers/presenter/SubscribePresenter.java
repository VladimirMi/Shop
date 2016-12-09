package ru.mikhalev.vladimir.mvpauth.core.layers.presenter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import ru.mikhalev.vladimir.mvpauth.core.base.BaseViewPresenter;
import ru.mikhalev.vladimir.mvpauth.root.IRootView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 05.12.2016.
 */

public abstract class SubscribePresenter<V extends ViewGroup> extends BaseViewPresenter<V> {

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
