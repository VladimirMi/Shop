package ru.mikhalev.vladimir.mvpauth.core.base;

import android.content.Context;
import android.databinding.BaseObservable;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.core.App;

/**
 * Developer Vladimir Mikhalev, 09.12.2016.
 */

public class BaseViewModel extends BaseObservable {
    @Inject
    protected Context mContext;

    public BaseViewModel() {
        App.getAppComponent().inject(this);
    }
}
