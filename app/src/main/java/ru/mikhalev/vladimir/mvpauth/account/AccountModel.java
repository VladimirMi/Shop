package ru.mikhalev.vladimir.mvpauth.account;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.address.AddressDto;
import ru.mikhalev.vladimir.mvpauth.core.layers.model.AbstractModel;
import ru.mikhalev.vladimir.mvpauth.core.managers.PreferencesManager;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountModel extends AbstractModel {

    //region =============== Addresses ==============

    public Observable<AddressDto> getAddressObs() {
        return Observable.from(getAccountAddresses());
    }

    public ArrayList<AddressDto> getAccountAddresses() {
        return mDataManager.getAccountAddresses();
    }

    public void updateOrInsertAddress(AddressDto address) {
        mDataManager.updateOrInsertAddress(address);
    }


    public void removeAddress(AddressDto address) {
        mDataManager.removeAddress(address);
    }

    public AddressDto getAddressFromPosition(int position) {
        return getAccountAddresses().get(position);
    }

    //endregion

    //region =============== Settings ==============

    public Observable<AccountSettingsDto> getAccountSettingsObs() {
        return Observable.just(getAccountSettings());
    }

    private AccountSettingsDto getAccountSettings() {
        return new AccountSettingsDto(mDataManager.getAccountSettings());
    }

    //endregion

    public AccountDto getAccountDto() {
        return null; //new AccountDto(getAccountProfileInfo(), getAccountAddresses(), getAccountSettings());
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

    private Map<String, String> getAccountProfileInfo() {
        return mDataManager.getAccountProfileInfo();
    }

}
