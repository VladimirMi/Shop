package ru.mikhalev.vladimir.mvpauth.core.managers;

import android.content.SharedPreferences;

import ru.mikhalev.vladimir.mvpauth.core.MvpApplication;
import ru.mikhalev.vladimir.mvpauth.core.utils.ConstantManager;

public class PreferencesManager {
    private SharedPreferences mSharedPreferences;

    public PreferencesManager() {
        mSharedPreferences = MvpApplication.getSharedPreferences();
    }

    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN_KEY, ConstantManager.INVALID_TOKEN);
    }

    public void clearAllData() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
