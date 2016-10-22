package ru.mikhalev.vladimir.mvpauth.data.managers;


import android.content.Context;

import ru.mikhalev.vladimir.mvpauth.utils.ConstantManager;

public class DataManager {
    private static DataManager ourInstance;
    private PreferencesManager mPreferencesManager;
    private Context mAppContext;

    private DataManager() {
        mPreferencesManager = new PreferencesManager();
        mAppContext = MvpApplication.getAppContext();
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Context getAppContext() {
        return mAppContext;
    }


    public boolean isAuthUser() {
        return !mPreferencesManager.getAuthToken().equals(ConstantManager.INVALID_TOKEN);
    }

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }
}
