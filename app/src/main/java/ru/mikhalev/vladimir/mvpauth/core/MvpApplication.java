package ru.mikhalev.vladimir.mvpauth.core;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MvpApplication extends Application {
    private static SharedPreferences sSharedPreferences;
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sAppContext = getApplicationContext();
    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    public static Context getAppContext() {
        return sAppContext;
    }
}
