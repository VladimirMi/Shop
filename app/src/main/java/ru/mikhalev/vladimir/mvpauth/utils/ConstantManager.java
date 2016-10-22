package ru.mikhalev.vladimir.mvpauth.utils;


public interface ConstantManager {

    String TAG_PREFIX = "DEV ";

    int LOGIN_STATE = 0;
    int IDLE_STATE = 1;

    // Preferences Keys
    String AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY";

    String INVALID_TOKEN = "";


    String LOADER_VISIBILE = "LOADER_VISIBILE";
}
