package ru.mikhalev.vladimir.mvpauth.root;

import android.support.annotation.Nullable;

import ru.mikhalev.vladimir.mvpauth.core.layers.view.IView;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */
public interface IRootView extends IView {

    void showMessage(String message);

    void showError(Throwable e);

    void showLoad();

    void hideLoad();

    @Nullable
    IView getCurrentScreen();

    void setBasketCounter(int count);
}
