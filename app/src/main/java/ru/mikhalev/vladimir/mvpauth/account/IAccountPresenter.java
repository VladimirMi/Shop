package ru.mikhalev.vladimir.mvpauth.account;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IAccountPresenter {

    void editAddress(int position);

    void clickOnAddAddress();

    void removeAddress(int position);

    void switchViewState();

    void switchOrder(boolean isCheked);

    void switchPromo(boolean isCheked);

    void changeAvatar();

    void chooseCamera();

    void chooseGallery();
}