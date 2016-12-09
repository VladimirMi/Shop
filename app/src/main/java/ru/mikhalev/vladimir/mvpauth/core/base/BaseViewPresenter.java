package ru.mikhalev.vladimir.mvpauth.core.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import mortar.MortarScope;
import mortar.ViewPresenter;
import ru.mikhalev.vladimir.mvpauth.root.IRootView;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 09.12.2016.
 */

public abstract class BaseViewPresenter<V extends ViewGroup> extends ViewPresenter<V> {

    @Nullable
    protected abstract IRootView getRootView();

    @Override
    @CallSuper
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        Timber.d("onLoad");
    }

    @Override
    @CallSuper
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        Timber.d("onEnterScope");
    }

    @Override
    @CallSuper
    protected void onExitScope() {
        super.onExitScope();
        Timber.d("onExitScope");
    }
}
