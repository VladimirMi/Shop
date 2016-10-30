package ru.mikhalev.vladimir.mvpauth.auth;

public interface IAuthPresenter{
    void clickOnLogin();
    void clickOnFb();
    void clickOnVk();
    void clickOnTwitter();
    void clickOnShowCatalog();

    boolean checkUserAuth();

    boolean validateEmail();

    boolean validatePassword();
}
