package ru.mikhalev.vladimir.mvpshop.features.auth;


import ru.mikhalev.vladimir.mvpshop.core.BaseModel;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

public class AuthModel extends BaseModel {

    public void loginUser(String email, String password) {
        mDataManager.loginUser(email, password);
    }

    public Observable<AccountRealm> getAccountObs() {
        return mDataManager.getAccountFromDB();
    }
}
