package ru.mikhalev.vladimir.mvpauth.mvp.models;


import ru.mikhalev.vladimir.mvpauth.data.managers.DataManager;

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
