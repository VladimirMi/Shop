package ru.mikhalev.vladimir.mvpauth.mvp.views;


import android.support.annotation.Nullable;

import ru.mikhalev.vladimir.mvpauth.mvp.presenters.IAuthPresenter;
import ru.mikhalev.vladimir.mvpauth.ui.views.AuthPanel;

public interface IAuthView {

    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();

    IAuthPresenter getPresenter();

    void showLoginBtn();
    void hideLoginBtn();

    @Nullable
    AuthPanel getAuthPanel();

    void startFbAnimation();
    void startTwAnimation();
    void startVkAnimation();

    void requestEmailFocus();

    void requestPasswordFocus();

    void setEmailError(String error);

    void setPasswordError(String error);
}
