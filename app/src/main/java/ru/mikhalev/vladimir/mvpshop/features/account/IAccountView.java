package ru.mikhalev.vladimir.mvpshop.features.account;

import ru.mikhalev.vladimir.mvpshop.core.IView;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IAccountView extends IView {

    AddressListAdapter getAdapter();

    void setViewModel(AccountViewModel viewModel);

    void changeState();

    void showPhotoSourceDialog();

    AccountViewModel getViewModel();
}
