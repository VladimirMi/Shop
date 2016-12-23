package ru.mikhalev.vladimir.mvpshop.data;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Developer Vladimir Mikhalev, 09.12.2016.
 */
public interface AsyncTransformer {

    <T> Observable.Transformer<T, T> transform();

    AsyncTransformer DEFAULT = new AsyncTransformer() {
        @Override
        public <T> Observable.Transformer<T, T> transform() {
            return tObservable -> tObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };
}

