package ru.mikhalev.vladimir.mvpshop.utils;


import java.util.regex.Pattern;

import ru.mikhalev.vladimir.mvpshop.BuildConfig;

public interface AppConfig {

    // Validation patterns
    Pattern EMAIL_ADDRESS_VALIDATE = Pattern.compile(
            "[a-zA-Z0-9\\+\\._%\\-]{3,256}" +
                    "@[a-zA-Z0-9]{2,64}" +
                    "\\.[a-zA-Z0-9]{2,25}"
    );

    Pattern PASSWORD_VALIDATE = Pattern.compile(
            "[a-zA-Z0-9@#$%!]{8,}"
    );

    String BASE_URL = "https://skba1.mgbeta.ru/api/v1/";
    int CONNECT_TIMEOUT = 5000;
    int READ_TIMEOUT = 5000;
    int WRITE_TIMEOUT = 5000;

    String FILE_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider";
    String PHOTO_FILE_NAME_PREFIX = "IMG_";
    String PHOTO_FILE_NAME_SUFFIX = ".jpg";

    String REALM_USER = "test@mail.ru";
    String REALM_PASSWORD = "test";
    String REALM_AUTH_URL = "http://192.168.1.162:9080/auth";
    String REALM_DB_URL = "realm://192.168.1.162:9080/~/default";
}
