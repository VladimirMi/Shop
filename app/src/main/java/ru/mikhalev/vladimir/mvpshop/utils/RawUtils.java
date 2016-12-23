package ru.mikhalev.vladimir.mvpshop.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kenrube
 * @since 08.11.16
 */

public class RawUtils {

    @Nullable
    public static String getJson(Context context, @RawRes int rawId) {
        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(rawId);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return new String(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
