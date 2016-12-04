package ru.mikhalev.vladimir.mvpauth.core.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.StringDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.address.AddressViewModel;

public class PreferencesManager {
    private final SharedPreferences mSharedPreferences;
    private static final String AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY";

    @StringDef({
            ACCOUNT.FULL_NAME_KEY,
            ACCOUNT.AVATAR_KEY,
            ACCOUNT.PHONE_KEY,
            ACCOUNT.NOTIFICATION_ORDER_KEY,
            ACCOUNT.NOTIFICATION_PROMO_KEY})
    public @interface ACCOUNT {

        String FULL_NAME_KEY = "PROFILE_FULL_NAME_KEY";

        String AVATAR_KEY = "PROFILE_AVATAR_KEY";
        String PHONE_KEY = "PROFILE_PHONE_KEY";
        String NOTIFICATION_ORDER_KEY = "NOTIFICATION_ORDER_KEY";
        String NOTIFICATION_PROMO_KEY = "NOTIFICATION_PROMO_KEY";
    }

    private static final String[] USER_INFO = {
            ACCOUNT.FULL_NAME_KEY,
            ACCOUNT.AVATAR_KEY,
            ACCOUNT.PHONE_KEY
    };
    private static final String[] USER_SETTINGS = {
            ACCOUNT.NOTIFICATION_ORDER_KEY,
            ACCOUNT.NOTIFICATION_PROMO_KEY
    };

    public PreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(AUTH_TOKEN_KEY, "");
    }

    public void saveAccountSetting(String notificationPromoKey, boolean isChecked) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(notificationPromoKey, isChecked);
        editor.apply();
    }

    public void saveProfileInfo(String name, String phone) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ACCOUNT.FULL_NAME_KEY, name);
        editor.putString(ACCOUNT.PHONE_KEY, phone);
        editor.apply();
    }

    // TODO: 01.12.2016 implement saving addresses
    public void addAddress(AddressViewModel addressViewModel) {

    }

    public void saveAvatarPhoto(String photoPath) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ACCOUNT.AVATAR_KEY, photoPath);
        editor.apply();
    }

    public Map<String, String> getUserAccountProfileInfo() {
        Map<String, String> result = new HashMap<>();
        for (String key : USER_INFO) {
            result.put(key, mSharedPreferences.getString(key, "null"));
        }
        return result;
    }

    // TODO: 01.12.2016 implement loading addresses
    public ArrayList<AddressViewModel> getAccountAddresses() {
        return null;
    }

    public Map<String, Boolean> getAccountSettings() {
        Map<String, Boolean> result = new HashMap<>();
        for (String key : USER_SETTINGS) {
            result.put(key, mSharedPreferences.getBoolean(key, false));
        }
        return result;
    }


    public void clearAllData() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
