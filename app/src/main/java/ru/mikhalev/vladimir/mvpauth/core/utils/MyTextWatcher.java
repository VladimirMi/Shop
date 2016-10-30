package ru.mikhalev.vladimir.mvpauth.core.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.auth.AuthPresenter;
import ru.mikhalev.vladimir.mvpauth.auth.IAuthPresenter;

public class MyTextWatcher implements TextWatcher {
    private static final String TAG = ConstantManager.TAG_PREFIX + "MyTextWatcher";
    private final EditText mEditText;
    private IAuthPresenter mPresenter;

    public MyTextWatcher(EditText editText) {
        mEditText = editText;
        mPresenter = AuthPresenter.getInstance();
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        switch (mEditText.getId()) {
            case R.id.login_email_et:
                mPresenter.validateEmail();
                break;
            case R.id.login_password_et:
                mPresenter.validatePassword();
                break;
        }
    }
}
