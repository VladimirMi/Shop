package ru.mikhalev.vladimir.mvpauth.core.layers.view;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

/**
 * Developer Vladimir Mikhalev, 30.10.2016.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {
    private ViewDataBinding mBinding;

    public ViewDataBinding getBinding() {
        return mBinding;
    }

    public void setBinding(ViewDataBinding binding) {
        mBinding = binding;
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
