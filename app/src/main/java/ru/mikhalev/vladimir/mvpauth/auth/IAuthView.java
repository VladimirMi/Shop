package ru.mikhalev.vladimir.mvpauth.auth;


import android.support.annotation.Nullable;

import ru.mikhalev.vladimir.mvpauth.core.base.view.IView;

public interface IAuthView extends IView {

    void showLoginBtn();

    void hideLoginBtn();

    @Nullable
    AuthPanel getAuthPanel();

    void showEmailError(boolean error);

    void showPasswordError(boolean error);

    void showCatalogScreen();
}
