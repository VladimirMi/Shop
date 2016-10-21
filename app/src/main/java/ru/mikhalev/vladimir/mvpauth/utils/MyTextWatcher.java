package ru.mikhalev.vladimir.mvpauth.utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import ru.mikhalev.vladimir.mvpauth.R;

public class MyTextWatcher implements TextWatcher {
    private static final String TAG = ConstantManager.TAG_PREFIX + "MyTextWatcher";
    private final EditText mEditText;
    private final Context mContext;
    private TextInputLayout mTextInputLayout;

    public MyTextWatcher(Context context, EditText editText) {
        mEditText = editText;
        mContext = context;
        mTextInputLayout = (TextInputLayout) mEditText.getParent().getParent();
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        switch (mEditText.getId()) {
            case R.id.login_email_et:
                validateEmail(String.valueOf(editable));
                break;
            case R.id.login_password_et:
                validatePassword(String.valueOf(editable));
                break;
        }
    }

    private void validateEmail(String email) {
        if (!isValidEmail(email)) {
            mTextInputLayout.setHint(mContext.getString(R.string.err_msg_email));
        } else {
            mTextInputLayout.setHint(mContext.getString(R.string.email_hint));
        }
    }

    private void validatePassword(String password) {
        if (!isValidPassword(password)) {
            mTextInputLayout.setHint(mContext.getString(R.string.err_msg_password));
        } else {
            mTextInputLayout.setHint(mContext.getString(R.string.password_hint));
        }
    }

    public static boolean isValidEmail(String email) {
        return !email.trim().isEmpty() &&
                AppConfig.EMAIL_ADDRESS_VALIDATE.matcher(email.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return !password.trim().isEmpty() &&
                AppConfig.PASSWORD_VALIDATE.matcher(password.trim()).matches();
    }
}
