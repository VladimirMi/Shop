package ru.mikhalev.vladimir.mvpshop.features.account;

import ru.mikhalev.vladimir.mvpshop.core.BaseModel;
import ru.mikhalev.vladimir.mvpshop.data.jobs.UploadAvatarJob;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountModel extends BaseModel {

    public Observable<AccountRealm> getAccountObs() {
        return mDataManager.getAccountFromDB();
    }

    public void removeAddress(AddressRealm addressRealm) {
        mDataManager.deleteFromDB(AddressRealm.class, addressRealm.getId());
    }

    public void saveAccount(AccountRealm accountRealm) {
        mDataManager.saveAccountInDB(accountRealm);
        if (!accountRealm.getAvatar().contains("http")) {
            uploadAvatar(accountRealm.getAvatar());
        }
    }

    private void uploadAvatar(String currentAvatarPath) {
        mJobManager.addJobInBackground(new UploadAvatarJob(currentAvatarPath));
    }

    public boolean isUserAuth() {
        return !mDataManager.getToken().isEmpty();
    }

    //endregion
}
