package ru.mikhalev.vladimir.mvpshop.data.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

import ru.mikhalev.vladimir.mvpshop.utils.StringUtils;

@SuppressWarnings("WeakerAccess")
public class PreferencesManager {
    private final SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    private static final String AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY";
    private static final String PRODUCT_LAST_UPDATE_KEY = "PRODUCT_LAST_UPDATE_KEY";


    @SuppressLint("CommitPrefEdits")
    public PreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public void saveAuthToken(String authToken) {
        mEditor.putString(AUTH_TOKEN_KEY, authToken);
        mEditor.commit();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(AUTH_TOKEN_KEY, StringUtils.EMPTY);
    }

    public void saveLastProductUpdate(String date) {
        mEditor.putString(PRODUCT_LAST_UPDATE_KEY, date);
        mEditor.commit();
    }

    public String getLastProductUpdate() {
        return mSharedPreferences.getString(PRODUCT_LAST_UPDATE_KEY, new Date(0).toString());
    }
}
