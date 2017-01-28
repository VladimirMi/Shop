package ru.mikhalev.vladimir.mvpshop.utils;


import java.util.regex.Pattern;

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

    String FILE_PROVIDER_AUTHORITY = "ru.mikhalev.vladimir.mvpshop.fileprovider";
    String PHOTO_FILE_NAME_PREFIX = "IMG_";
    String PHOTO_FILE_NAME_SUFFIX = ".jpg";
}
