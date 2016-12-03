package ru.mikhalev.vladimir.mvpauth.account;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Developer Vladimir Mikhalev 02.12.2016
 */

public class AvatarBehavior extends AppBarLayout.ScrollingViewBehavior {
    private static final String TAG = "AvatarBehavior";
    private float mInitialAvatarCenterY;

    public AvatarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        mInitialAvatarCenterY = child.getY() + child.getHeight() / 2;
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        CoordinatorLayout.LayoutParams lp = ((CoordinatorLayout.LayoutParams) child.getLayoutParams());
        float scale = dependency.getBottom() / mInitialAvatarCenterY;
        child.setScaleX(scale);
        child.setScaleY(scale);

        child.setY(dependency.getBottom() - child.getHeight() / 2);
        return false;
    }
}
