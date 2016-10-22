package ru.mikhalev.vladimir.mvpauth.mvp.presenters;

import android.support.annotation.Nullable;

import ru.mikhalev.vladimir.mvpauth.mvp.views.IAuthView;

public interface IAuthPresenter {

    void takeView(IAuthView authView);

    void dropView();
    void initView();

    @Nullable
    IAuthView getView();

    void clickOnLogin();
    void clickOnFb();
    void clickOnVk();
    void clickOnTwitter();
    void clickOnShowCatalog();

    boolean checkUserAuth();

    void validateEmail();

    void validatePassword();
}
