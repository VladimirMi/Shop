package ru.mikhalev.vladimir.mvpauth.account;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IAccountPresenter {

    void clickOnAddress();

    void switchViewState();

    void switchOrder(boolean isCheked);

    void switchPromo(boolean isCheked);

    void takePhoto();

    void chooseCamera();

    void chooseGallery();

}
