package ru.mikhalev.vladimir.mvpshop.utils;

/**
 * Developer Vladimir Mikhalev, 13.12.2016.
 */
public class StringUtils {
    public static final String EMPTY = "";

    public static boolean isNotNullOrEmpty(String firstName) {
        return firstName != null && !firstName.isEmpty();
    }
}
