package ru.mikhalev.vladimir.mvpshop.features.account;

import ru.mikhalev.vladimir.mvpshop.core.BaseModel;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountAddressDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountProfileDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountSettingsDto;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountModel extends BaseModel {
    private BehaviorSubject<AccountViewModel> mAccountSubject = BehaviorSubject.create(getAccountViewModel());

    public BehaviorSubject<AccountViewModel> getAccountSubject() {
        return mAccountSubject;
    }

    //region ==================== AccountRes ========================

    public AccountViewModel getAccountViewModel() {
        return new AccountViewModel(getAccountProfile(), getAccountSettings());
    }

    private AccountProfileDto getAccountProfile() {
        return mDataManager.getAccountProfile();
    }

    private AccountSettingsDto getAccountSettings() {
        return mDataManager.getAccountSettings();
    }

    public void saveAccountProfile(AccountViewModel viewModel) {
        mDataManager.saveAccountProfile(new AccountProfileDto(viewModel));
        mAccountSubject.onNext(viewModel);
    }

    public void saveAccountSetting(AccountViewModel viewModel) {
        mDataManager.saveAccountSettings(new AccountSettingsDto(viewModel));
    }

    //endregion

    //region =============== Addresses ==============

    public Observable<AccountAddressDto> getAccountAddresses() {
        return mDataManager.getAccountAddresses().compose(mAsyncTransformer.transform());
    }

    public void updateOrInsertAddress(AccountAddressDto address) {
        mDataManager.updateOrInsertAddress(address);
    }


    public void removeAddress(AccountAddressDto address) {
        mDataManager.removeAddress(address);
    }

    public AccountAddressDto getAddressFromPosition(int position) {
        return mDataManager.getAccountAddressFromPosition(position);
    }

    //endregion
}
