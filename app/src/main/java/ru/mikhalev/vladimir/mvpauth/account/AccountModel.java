package ru.mikhalev.vladimir.mvpauth.account;

import java.util.ArrayList;
import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.address.AddressViewModel;
import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.core.managers.PreferencesManager;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountModel extends BaseModel {
    @SuppressWarnings("unused")
    private static final String TAG = "AccountModel";
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

    public Observable<AddressViewModel> getAddressObs() {
        return Observable.from(getAccountAddresses()).compose(mAsyncTransformer.transform());
    }

    private ArrayList<AddressViewModel> getAccountAddresses() {
        return mDataManager.getAccountAddresses();
    }

    public void updateOrInsertAddress(AddressViewModel address) {
        mDataManager.updateOrInsertAddress(address);
    }


    public void removeAddress(AddressViewModel address) {
        mDataManager.removeAddress(address);
    }

    public AddressViewModel getAddressFromPosition(int position) {
        return getAccountAddresses().get(position);
    }

    //endregion
}
