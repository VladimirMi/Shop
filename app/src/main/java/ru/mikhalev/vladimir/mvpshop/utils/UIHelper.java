package ru.mikhalev.vladimir.mvpshop.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.util.ArrayList;

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

    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    public static ArrayList<View> getChildrenExcludeView(ViewGroup parent, @IdRes int ... excludeChilds) {
        ArrayList<View> children = new ArrayList<>();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            for (int excludeChild : excludeChilds) {
                if (child.getId() != excludeChild) {
                    children.add(child);
                }
            }
        }
        return children;
    }

    public static void waitForMeasure(View view, OnMeasureCallback callback) {
        int width = view.getWidth();
        int height = view.getHeight();

        if (width > 0 && height > 0) {
            callback.onMeasure(view, width, height);
            return;
        }

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final ViewTreeObserver observer = view.getViewTreeObserver();
                if (observer.isAlive()) {
                    observer.removeOnPreDrawListener(this);
                }
                callback.onMeasure(view, view.getWidth(), view.getHeight());
                return false;
            }
        });
    }

    public interface OnMeasureCallback {
        void onMeasure(View view, int width, int height);
    }
}
