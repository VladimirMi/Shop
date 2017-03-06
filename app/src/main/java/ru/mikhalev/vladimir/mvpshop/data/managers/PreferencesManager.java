package ru.mikhalev.vladimir.mvpshop.data.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
public class PreferencesManager {
    private final SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    private static final String AUTH_TOKEN = "AUTH_TOKEN";
    private static final String PRODUCT_LAST_UPDATE_KEY = "PRODUCT_LAST_UPDATE_KEY";


    @SuppressLint("CommitPrefEdits")
    public PreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public void saveLastProductUpdate(String date) {
        mEditor.putString(PRODUCT_LAST_UPDATE_KEY, date);
        mEditor.apply();
    }

    public String getLastProductUpdate() {
        return mSharedPreferences.getString(PRODUCT_LAST_UPDATE_KEY, new Date(0).toString());
    }

    public String getToken() {
        return mSharedPreferences.getString(AUTH_TOKEN, "");
    }

    public void saveToken(String token) {
        mEditor.putString(AUTH_TOKEN, token);
        mEditor.apply();
    }
}
