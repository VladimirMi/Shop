package ru.mikhalev.vladimir.mvpauth.auth;

public interface IAuthPresenter{
    void clickOnFb();
    void clickOnVk();
    void clickOnTwitter();

    void clickOnShowCatalog();

    boolean checkUserAuth();

    void clickOnLogin(AuthViewModel viewModel);
}
