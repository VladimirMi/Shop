package ru.mikhalev.vladimir.mvpshop.core;

import android.support.annotation.Nullable;

/**
 * Developer Vladimir Mikhalev, 27.10.2016
 */

public abstract class AbstractPresenter<T extends IView> {
    private T mView;

    public void takeView(T view) {
        mView = view;
    }

    public void dropView() {
        mView = null;
    }

    @Nullable
    public T getView() {
        return mView;
    }

    public abstract void initView();
}
