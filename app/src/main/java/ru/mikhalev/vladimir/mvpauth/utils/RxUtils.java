package ru.mikhalev.vladimir.mvpauth.utils;

import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import rx.Emitter;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev, 13.12.2016.
 */

public class RxUtils {

    public static <T> Observable<T> toObservable(@NonNull final ObservableField<T> observableField) {
        return Observable.fromEmitter(tEmitter -> {
            final OnPropertyChangedCallback callback = new OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(android.databinding.Observable dataBindingObservable, int propertyId) {
                    if (dataBindingObservable == observableField) {
                        tEmitter.onNext(observableField.get());
                    }
                }
            };
            observableField.addOnPropertyChangedCallback(callback);
            tEmitter.setCancellation(() -> observableField.removeOnPropertyChangedCallback(callback));
        }, Emitter.BackpressureMode.BUFFER);
    }
}
