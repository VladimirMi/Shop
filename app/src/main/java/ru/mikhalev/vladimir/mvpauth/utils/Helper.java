package ru.mikhalev.vladimir.mvpauth.utils;


public class Helper {


    public static boolean isValidEmail(String email) {
        return !email.trim().isEmpty() &&
                AppConfig.EMAIL_ADDRESS_VALIDATE.matcher(email.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return !password.trim().isEmpty() &&
                AppConfig.PASSWORD_VALIDATE.matcher(password.trim()).matches();
    }
}
