package ru.mikhalev.vladimir.mvpauth.auth;


import ru.mikhalev.vladimir.mvpauth.core.layers.model.AbsModel;

public class AuthModel extends AbsModel {

    public  boolean isAuthUser() {
        return mDataManager.isAuthUser();
    }

    public void loginUser(String email, String password) {
        mDataManager.loginUser(email, password);
    }
}
