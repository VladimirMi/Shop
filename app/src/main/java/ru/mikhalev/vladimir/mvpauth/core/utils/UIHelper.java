package ru.mikhalev.vladimir.mvpauth.core.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Developer Vladimir Mikhalev 10.12.2016
 */

public class UIHelper {

    public static int getActionBarHeight(Context context) {
        int result = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return result;
    }
}
