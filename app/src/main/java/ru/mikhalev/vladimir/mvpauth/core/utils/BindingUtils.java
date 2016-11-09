package ru.mikhalev.vladimir.mvpauth.core.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class BindingUtils {

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, @DrawableRes int resId) {
        MyGlideModule.setImage(imageView.getContext(), resId, imageView);
    }

    @BindingAdapter("avatar")
    public static void setAvatar(ImageView imageView, @DrawableRes int resId) {
        MyGlideModule.setUserAvatar(imageView.getContext(), resId, imageView);
    }

    @BindingAdapter("font")
    public static void setFont(TextView textView, String  fontName) {
        Typeface tf = Typeface.createFromAsset(textView.getContext().getAssets(),
                "fonts/" + fontName);
        textView.setTypeface(tf);
    }

    @BindingConversion
    public static int convertStateToVisibility(final boolean state) {
        return state ? View.VISIBLE : View.GONE;
    }
}
