package ru.mikhalev.vladimir.mvpshop.features.account;

import java.util.Map;

import ru.mikhalev.vladimir.mvpshop.core.BaseModel;
import ru.mikhalev.vladimir.mvpshop.data.managers.PreferencesManager;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AddressRes;
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

    //region ==================== AccountRes ========================

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

    public Observable<AddressRes> getAccountAddresses() {
        return mDataManager.getAccountAddresses().compose(mAsyncTransformer.transform());
    }

    public void updateOrInsertAddress(AddressRes address) {
        mDataManager.updateOrInsertAddress(address);
    }


    public void removeAddress(Observable<AddressRes> address) {
        mDataManager.removeAddress(address);
    }

    public Observable<AddressRes> getAddressFromPosition(int position) {
        return mDataManager.getAccountAddressFromPosition(position);
    }

    //endregion
}
