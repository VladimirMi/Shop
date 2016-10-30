package ru.mikhalev.vladimir.mvpauth.auth;


import ru.mikhalev.vladimir.mvpauth.core.managers.DataManager;

public class AuthModel {
    private DataManager mDataManager;

    public AuthModel() {
        mDataManager = DataManager.getInstance();
    }

    public  boolean isAuthUser() {
        return mDataManager.isAuthUser();
    }

    public void loginUser(String email, String password) {
        mDataManager.loginUser(email, password);
    }
}
