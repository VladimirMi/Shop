package ru.mikhalev.vladimir.mvpauth.core.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.utils.UiHelper;


public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        UiHelper.setCustomFont(this, context, attrs,
                R.styleable.CustomFontTextView, R.styleable.CustomFontTextView_font);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        UiHelper.setCustomFont(this, context, attrs,
                R.styleable.CustomFontTextView, R.styleable.CustomFontTextView_font);
    }
}
