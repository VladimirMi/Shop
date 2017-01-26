package ru.mikhalev.vladimir.mvpshop.features.account;

import ru.mikhalev.vladimir.mvpshop.core.IView;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IAccountView extends IView {

    void setViewModel(AccountRealm accountRealm);

    void showAddress(AddressRealm addressRealm);
}
