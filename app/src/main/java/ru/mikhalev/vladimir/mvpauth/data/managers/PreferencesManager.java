package ru.mikhalev.vladimir.mvpauth.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.StringDef;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import java.util.Date;
import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.utils.StringUtils;

public class PreferencesManager {
    private static final String PRODUCT_LAST_UPDATE_KEY = "PRODUCT_LAST_UPDATE_KEY";
    private final RxSharedPreferences mRxPreferences;
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        mRxPreferences = RxSharedPreferences.create(preferences);
    }

    public void saveAuthToken(String authToken) {
//        SharedPreferences.Editor editor = mRxPreferences.edit();
//        editor.putString(AUTH_TOKEN_KEY, authToken);
//        editor.apply();
    }

    public Preference<String> getAuthTokenPref() {
        return mRxPreferences.getString(AUTH_TOKEN_KEY, StringUtils.EMPTY);
    }

    public Preference<String> getLastProductUpdate() {
        return mRxPreferences.getString(PRODUCT_LAST_UPDATE_KEY, new Date(0).toString());
    }

    public void saveAccountSetting(String notificationPromoKey, boolean isChecked) {
//        SharedPreferences.Editor editor = mRxPreferences.edit();
//        editor.putBoolean(notificationPromoKey, isChecked);
//        editor.apply();
    }

    public void saveProfileInfo(String name, String phone) {
//        SharedPreferences.Editor editor = mRxPreferences.edit();
//        editor.putString(ACCOUNT.FULL_NAME_KEY, name);
//        editor.putString(ACCOUNT.PHONE_KEY, phone);
//        editor.apply();
    }

    public void saveAvatarPhoto(String photoPath) {
//        SharedPreferences.Editor editor = mRxPreferences.edit();
//        editor.putString(ACCOUNT.AVATAR_KEY, photoPath);
//        editor.apply();
    }

    public Map<String, String> getUserAccountProfileInfo() {
//        Map<String, String> result = new HashMap<>();
//        for (String key : USER_INFO) {
//            result.put(key, mRxPreferences.getString(key, "null"));
//        }
//        return result;
        return null;
    }

    public Map<String, Boolean> getAccountSettings() {
//        Map<String, Boolean> result = new HashMap<>();
//        for (String key : USER_SETTINGS) {
//            result.put(key, mRxPreferences.getBoolean(key, false));
//        }
//        return result;
        return null;
    }

    public void clearAllData() {
//        SharedPreferences.Editor editor = mRxPreferences.edit();
//        editor.clear();
//        editor.apply();
    }
}
