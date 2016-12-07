package ru.mikhalev.vladimir.mvpauth.core.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class BindingUtils {
    private static final String TAG = "BindingUtils";

    @BindingAdapter("onClick")
    public static void bindOnClick(View view, Runnable runnable) {
        view.setOnClickListener(v -> runnable.run());
    }

    @BindingAdapter("error")
    public static void bindError(TextInputLayout textInputLayout, final String error) {
        textInputLayout.setErrorEnabled(error != null);
        textInputLayout.setError(error);
    }

    @BindingAdapter("image")
    public static void bindImage(ImageView imageView, String path) {
        MyGlideModule.setImage(path, imageView);
    }

    @BindingAdapter("avatar")
    public static void bindAvatar(ImageView imageView, String path) {
        MyGlideModule.setUserAvatar(path, imageView);
    }

    @BindingAdapter({"avatar", "alpha"})
    public static void bindAvatarWithAlpha(ImageView imageView, String path, float alpha) {
        MyGlideModule.setUserAvatarWithAlpha(path, imageView, alpha);
    }

    @BindingAdapter("font")
    public static void bindFont(TextView textView, String fontName) {
        Typeface tf = Typeface.createFromAsset(textView.getContext().getAssets(),
                "fonts/" + fontName);
        textView.setTypeface(tf);
    }

    @BindingConversion
    public static int convertStateToVisibility(final boolean state) {
        return state ? View.VISIBLE : View.GONE;
    }
}
