package ru.mikhalev.vladimir.mvpauth.account;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.address.AddressDto;
import ru.mikhalev.vladimir.mvpauth.core.layers.model.AbstractModel;
import ru.mikhalev.vladimir.mvpauth.core.managers.PreferencesManager;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountModel extends AbstractModel {

    public AccountViewModel getAccountViewModel() {
        return new AccountViewModel(getAccountProfileInfo(), getAccountAddresses(), getAccountSettings());
    }

    public void saveProfileInfo(String name, String phone) {
        mDataManager.saveProfileInfo(name, phone);
    }

    public void saveAvatarPhoto(Uri photoUri) {
        // TODO: 01.12.2016 implement avatar saving
        mDataManager.saveAvatarPhoto(photoUri);
    }

    public void savePromoNotification(boolean isChecked) {
        mDataManager.saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY, isChecked);
    }

    public void saveOrderNotification(boolean isChecked) {
        mDataManager.saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY, isChecked);
    }

    public void addAddress(AddressDto addressDto) {
        mDataManager.addAddress(addressDto);
    }

    // TODO: 01.12.2016 remove address
    public void removeAddress() {

    }

    private Map<String, String> getAccountProfileInfo() {
        return mDataManager.getAccountProfileInfo();
    }

    private ArrayList<AddressDto> getAccountAddresses() {
        return mDataManager.getAccountAddresses();
    }

    private Map<String, Boolean> getAccountSettings() {
        return mDataManager.getAccountSettings();
    }
}
