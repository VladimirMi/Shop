package ru.mikhalev.vladimir.mvpauth.core.layers.view;

/**
 * Developer Vladimir Mikhalev, 27.10.16
 */

public interface IView {
    void showMessage(String message);

    void showError(Throwable e);

    void showLoad();

    void hideLoad();
}
