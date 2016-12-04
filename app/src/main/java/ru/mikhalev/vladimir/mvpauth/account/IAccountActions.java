package ru.mikhalev.vladimir.mvpauth.account;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IAccountActions {

    void switchViewState();

    void clickOnAddAddress();

    void switchOrder(boolean isCheked);

    void switchPromo(boolean isCheked);

    void changeAvatar();
}
