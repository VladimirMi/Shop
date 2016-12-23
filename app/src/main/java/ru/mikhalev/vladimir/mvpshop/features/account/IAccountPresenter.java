package ru.mikhalev.vladimir.mvpshop.features.account;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IAccountPresenter {

    void editAddress(int position);

    void clickOnAddAddress();

    void removeAddress(int position);

    void switchViewState();

    void switchNotification();

    void changeAvatar();

    void chooseCamera();

    void chooseGallery();
}
