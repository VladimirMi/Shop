package ru.mikhalev.vladimir.mvpshop.features.account;

import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IAccountPresenter {

    void saveAccount(AccountRealm accountRealm);

    void removeAddress(AddressRealm addressRealm);

    void chooseCamera();

    void chooseGallery();
}
