package ru.mikhalev.vladimir.mvpauth.core.utils;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class BindingUtils {

    @BindingAdapter("setImage")
    public static void setImage(ImageView imageView, @DrawableRes int resId) {
        MyGlideModule.setImage(imageView.getContext(), resId, imageView);
    }

    @BindingAdapter("setAvatar")
    public static void setAvatar(ImageView imageView, int resId) {
        MyGlideModule.setUserAvatar(imageView.getContext(), resId, imageView);
    }
}
