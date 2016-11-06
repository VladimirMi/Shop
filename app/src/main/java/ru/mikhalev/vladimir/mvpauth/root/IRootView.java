package ru.mikhalev.vladimir.mvpauth.root;

import ru.mikhalev.vladimir.mvpauth.core.base.view.IView;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */
public interface IRootView extends IView {
    void showMessage(String message);

    void showError(Throwable e);

    void showLoad();

    void hideLoad();
}
