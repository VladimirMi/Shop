package ru.mikhalev.vladimir.mvpshop.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import ru.mikhalev.vladimir.mvpshop.R;

public class AspectRatioImageView extends ImageView {
    private final float DEFAULT_ASPECT_RATIO = 1.73f;
    private final float mAspectRatio;

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
        mAspectRatio = array.getFloat(R.styleable.AspectRatioImageView_aspect_ratio, DEFAULT_ASPECT_RATIO);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newWidth = getMeasuredWidth();
        int newHeight;
        if (mAspectRatio != 0) {
            newHeight =  (int) (newWidth / mAspectRatio);
        } else {
            newHeight = getMeasuredHeight();
        }

        setMeasuredDimension(newWidth, newHeight);
    }
}
