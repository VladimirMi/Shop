package ru.mikhalev.vladimir.mvpauth.account;

import ru.mikhalev.vladimir.mvpauth.core.layers.view.IView;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IAccountView extends IView {

    void changeState();

    void showPhotoSourceDialog();

    AccountDto getViewModel();
}
