package ru.mikhalev.vladimir.mvpauth.auth;


import com.f2prateek.rx.preferences.Preference;

import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.utils.StringUtils;
import rx.Observable;

public class AuthModel extends BaseModel {

    public Observable<Boolean> isAuthUser() {
        return getAuthTokenPref().asObservable()
                .map(StringUtils::isNotNullOrEmpty)
                .compose(mAsyncTransformer.transform());
    }

    public void loginUser(String email, String password) {
        mDataManager.loginUser(email, password);
    }

    private Preference<String> getAuthTokenPref() {
        return mDataManager.getAuthTokenPref();
    }
}
