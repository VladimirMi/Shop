package ru.mikhalev.vladimir.mvpshop.data.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.StringDef;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import ru.mikhalev.vladimir.mvpshop.data.dto.AccountAddressDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountProfileDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountSettingsDto;
import ru.mikhalev.vladimir.mvpshop.utils.StringUtils;

@SuppressWarnings("WeakerAccess")
public class PreferencesManager {
    private final SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;
    private Gson mGson = new Gson();

    private static final String AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY";
    private static final String PRODUCT_LAST_UPDATE_KEY = "PRODUCT_LAST_UPDATE_KEY";
    @StringDef({
            ACCOUNT.PROFILE_KEY,
            ACCOUNT.SETTINGS_KEY,
            ACCOUNT.ADDRESSES_KEY})
    public @interface ACCOUNT {
        String PROFILE_KEY = "PROFILE_KEY";
        String SETTINGS_KEY = "SETTINGS_KEY";
        String ADDRESSES_KEY = "ADDRESSES_KEY";
    }

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

    public void saveAccountProfile(AccountProfileDto profileDto) {
        mEditor.putString(ACCOUNT.PROFILE_KEY, mGson.toJson(profileDto));
        mEditor.commit();
    }

    public AccountProfileDto getAccountProfile() {
        String json = mSharedPreferences.getString(ACCOUNT.PROFILE_KEY, StringUtils.EMPTY);
        return mGson.fromJson(json, AccountProfileDto.class);
    }

    public void saveAccountSettings(AccountSettingsDto settingsDto) {
        mEditor.putString(ACCOUNT.SETTINGS_KEY, mGson.toJson(settingsDto));
        mEditor.commit();
    }

    public AccountSettingsDto getAccountSettings() {
        String json = mSharedPreferences.getString(ACCOUNT.SETTINGS_KEY, StringUtils.EMPTY);
        return mGson.fromJson(json, AccountSettingsDto.class);
    }

    public void saveAccountAddresses(List<AccountAddressDto> addressDtoList) {
        mEditor.putString(ACCOUNT.ADDRESSES_KEY, mGson.toJson(addressDtoList));
        mEditor.commit();
    }

    public List<AccountAddressDto> getAccountAddresses() {
        String json = mSharedPreferences.getString(ACCOUNT.PROFILE_KEY, StringUtils.EMPTY);
        Type listType = new TypeToken<List<AccountAddressDto>>() {
        }.getType();
        return mGson.fromJson(json, listType);
    }
}
