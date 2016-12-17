package ru.mikhalev.vladimir.mvpauth.auth;


import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.utils.StringUtils;

public class AuthModel extends BaseModel {

    public boolean isAuthUser() {
        return StringUtils.isNotNullOrEmpty(getAuthToken());
    }

    public void loginUser(String email, String password) {
        mDataManager.loginUser(email, password);
    }

    private String getAuthToken() {
        return mDataManager.getAuthTokenPref();
    }
}
