package ru.mikhalev.vladimir.mvpauth.core.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.mikhalev.vladimir.mvpauth.BuildConfig;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.base.view.IView;

/**
 * Developer Vladimir Mikhalev, 30.10.2016.
 */

public class BaseActivity extends AppCompatActivity implements IView {
    private static final String TAG = "BaseActivity";
    private ProgressDialog mProgressDialog;

    @Override
    public void showMessage(String message) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_container);
        if (coordinatorLayout != null) {
            Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "Coordinator container cannot be null");
        }
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage(getString(R.string.message_error_common));
            // TODO: 20-Oct-16 send error stacktrace to crashlytics
        }
    }

    @Override
    public void showLoad() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.layout.progress_bar);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mProgressDialog.setContentView(R.layout.progress_bar);
        mProgressDialog.show();
    }

    @Override
    public void hideLoad() {
        mProgressDialog.dismiss();
    }
}
