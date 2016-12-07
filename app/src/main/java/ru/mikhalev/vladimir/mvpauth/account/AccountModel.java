package ru.mikhalev.vladimir.mvpauth.account;

import java.util.ArrayList;
import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.address.AddressDto;
import ru.mikhalev.vladimir.mvpauth.core.layers.model.AbstractModel;
import ru.mikhalev.vladimir.mvpauth.core.managers.PreferencesManager;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountModel extends AbstractModel {
    private BehaviorSubject<AccountDto> mAccountSubject = BehaviorSubject.create();

    public AccountModel() {
        mAccountSubject.onNext(getAccountDto());
    }

    public BehaviorSubject<AccountDto> getAccountSubject() {
        return mAccountSubject;
    }

    //region ==================== Account ========================

    public AccountDto getAccountDto() {
        return new AccountDto(getAccountProfileInfo(), getAccountSettings());
    }

    private Map<String, String> getAccountProfileInfo() {
        return mDataManager.getAccountProfileInfo();
    }

    private Map<String, Boolean> getAccountSettings() {
        return mDataManager.getAccountSettings();
    }

    public void saveAccount(AccountDto viewModel) {
        mDataManager.saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY, viewModel.isOrderNotification());
        mDataManager.saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY, viewModel.isPromoNotification());
        mDataManager.saveAvatarPhoto(viewModel.getAvatar());
        mDataManager.saveProfileInfo(viewModel.getFullname(), viewModel.getPhone());
        mAccountSubject.onNext(viewModel);
    }

    //endregion

    //region =============== Addresses ==============

    public Observable<AddressDto> getAddressObs() {
        return Observable.from(getAccountAddresses());
    }

    private ArrayList<AddressDto> getAccountAddresses() {
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
}
