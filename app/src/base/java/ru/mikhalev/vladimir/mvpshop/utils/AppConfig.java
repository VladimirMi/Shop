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

    int MIN_CONSUMER_COUNT = 1;
    int MAX_CONSUMER_COUNT = 3;
    int LOAD_FACTOR = 3;
    int CONSUMER_KEEP_ALIVE = 120;
    int INITIAL_BACK_OFF_IN_MS = 1000;
    int UPDATE_INTERVAL_SEC = 60;
    int RETRY_REQUEST_BASE_DELAY = 500;
    int RETRY_REQUEST_COUNT = 5;
}
