package ru.mikhalev.vladimir.mvpshop.core;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

/**
 * Developer Vladimir Mikhalev, 30.10.2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ViewDataBinding mBinding;

    public ViewDataBinding getBinding() {
        return mBinding;
    }

    public void setBinding(ViewDataBinding binding) {
        mBinding = binding;
    }
}
