package ru.mikhalev.vladimir.mvpshop.core;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 27.10.16
 */

public abstract class BaseView<P extends BasePresenter> extends FrameLayout implements IView {
    @Inject protected P mPresenter;

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initDagger(context);
        }
    }

    protected abstract void initDagger(Context context);

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            initView();
        }
        Timber.tag(getClass().getSimpleName());
        Timber.d("onFinishInflate");
    }

    protected abstract void initView();

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
        Timber.tag(getClass().getSimpleName());
        Timber.d("onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
        Timber.tag(getClass().getSimpleName());
        Timber.d("onDetachedFromWindow");
    }
}
