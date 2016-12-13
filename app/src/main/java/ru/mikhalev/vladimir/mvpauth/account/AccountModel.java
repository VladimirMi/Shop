package ru.mikhalev.vladimir.mvpauth.account;

import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.data.dto.Address;
import ru.mikhalev.vladimir.mvpauth.data.managers.PreferencesManager;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountModel extends BaseModel {
    private BehaviorSubject<AccountViewModel> mAccountSubject = BehaviorSubject.create(getAccountDto());

    public BehaviorSubject<AccountViewModel> getAccountSubject() {
        return mAccountSubject;
    }

    //region ==================== Account ========================

    public AccountViewModel getAccountDto() {
        return new AccountViewModel(getAccountProfileInfo(), getAccountSettings());
    }

    private Map<String, String> getAccountProfileInfo() {
        return mDataManager.getAccountProfileInfo();
    }

    private Map<String, Boolean> getAccountSettings() {
        return mDataManager.getAccountSettings();
    }

    public void saveAccount(AccountViewModel viewModel) {
        mDataManager.saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY, viewModel.isOrderNotification());
        mDataManager.saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY, viewModel.isPromoNotification());
        mDataManager.saveAvatarPhoto(viewModel.getAvatar());
        mDataManager.saveProfileInfo(viewModel.getFullname(), viewModel.getPhone());
        mAccountSubject.onNext(viewModel);
    }

    //endregion

    //region =============== Addresses ==============

    public Observable<Address> getAccountAddresses() {
        return mDataManager.getAccountAddresses().compose(mAsyncTransformer.transform());
    }

    public void updateOrInsertAddress(Address address) {
        mDataManager.updateOrInsertAddress(address);
    }


    public void removeAddress(Observable<Address> address) {
        mDataManager.removeAddress(address);
    }

    public Observable<Address> getAddressFromPosition(int position) {
        return mDataManager.getAccountAddressFromPosition(position);
    }

    //endregion
}
