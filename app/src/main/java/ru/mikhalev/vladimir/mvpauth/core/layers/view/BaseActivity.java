package ru.mikhalev.vladimir.mvpauth.core.layers.view;

import android.app.ProgressDialog;
import android.databinding.ViewDataBinding;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import ru.mikhalev.vladimir.mvpauth.BuildConfig;
import ru.mikhalev.vladimir.mvpauth.R;

/**
 * Developer Vladimir Mikhalev, 30.10.2016.
 */

public class BaseActivity extends AppCompatActivity implements IView {
    private static final String TAG = "BaseActivity";
    private ProgressDialog mProgressDialog;
    private ViewDataBinding mBinding;

    public ViewDataBinding getBinding() {
        return mBinding;
    }

    public void setBinding(ViewDataBinding binding) {
        mBinding = binding;
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        showMessage(e.getLocalizedMessage());
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        } else {
            // TODO: 20-Oct-16 send error stacktrace to crashlytics
        }
    }

    @Override
    public void showLoad() {
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.progress_show_text),
                true, false);
    }

    @Override
    public void hideLoad() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
