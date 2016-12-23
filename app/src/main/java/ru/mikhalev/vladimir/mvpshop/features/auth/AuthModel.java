package ru.mikhalev.vladimir.mvpshop.features.auth;


import ru.mikhalev.vladimir.mvpshop.core.BaseModel;
import ru.mikhalev.vladimir.mvpshop.utils.StringUtils;

public class AuthModel extends BaseModel {

    public boolean isAuthUser() {
        return StringUtils.isNotNullOrEmpty(getAuthToken());
    }

    public void loginUser(String email, String password) {
        mDataManager.loginUser(email, password);
    }

    private String getAuthToken() {
        return mDataManager.getAuthToken();
    }
}
