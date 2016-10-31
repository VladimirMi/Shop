package ru.mikhalev.vladimir.mvpauth.core.base.presenter;

import android.databinding.BaseObservable;
import android.support.annotation.Nullable;

import ru.mikhalev.vladimir.mvpauth.core.base.view.IView;

/**
 * Developer Vladimir Mikhalev, 27.10.2016
 */

public abstract class AbstractPresenter<T extends IView> extends BaseObservable{
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
