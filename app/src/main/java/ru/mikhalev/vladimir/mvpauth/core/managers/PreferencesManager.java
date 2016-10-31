package ru.mikhalev.vladimir.mvpauth.core.managers;

import android.content.SharedPreferences;

import ru.mikhalev.vladimir.mvpauth.core.MvpApplication;

public class PreferencesManager {
    private static final String AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY";
    private SharedPreferences mSharedPreferences;

    public PreferencesManager() {
        mSharedPreferences = MvpApplication.getSharedPreferences();
    }

    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(AUTH_TOKEN_KEY, "");
    }

    public void clearAllData() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
