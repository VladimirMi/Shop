package ru.mikhalev.vladimir.mvpauth.utils;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class UiHelper {
    private static final String TAG = ConstantManager.TAG_PREFIX + "UiHelper";

    public static void setCustomFont(View textViewOrButton, Context ctx, AttributeSet attrs, int[] attributeSet, int fontId) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, attributeSet);
        String customFont = a.getString(fontId);
        setCustomFont(textViewOrButton, ctx, customFont);
        a.recycle();
    }

    private static boolean setCustomFont(View view, Context context, String asset) {
        if (asset.isEmpty())
            return false;
        Typeface typeface;
        try {
            typeface = getFont(context, asset);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
            if (view instanceof Button) {
                ((Button) view).setTypeface(typeface);
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: " + asset, e);
            return false;
        }

        return true;
    }

    private static HashMap<String, Typeface> fontCache = new HashMap<>();


    private static Typeface getFont(Context context, String fontName) {
        Typeface typeface = fontCache.get(fontName);

        if (typeface == null) {
            typeface = Typeface.createFromAsset(
                    context.getAssets(),
                    "fonts/" + fontName
            );

            fontCache.put(fontName, typeface);
        }

        return typeface;
    }
}
