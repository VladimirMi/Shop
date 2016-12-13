package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.res.Resources;
import android.databinding.Bindable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ru.mikhalev.vladimir.mvpauth.BR;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseViewModel;
import ru.mikhalev.vladimir.mvpauth.utils.AppConfig;

/**
 * @author kenrube
 * @since 06.11.16
 */

public class AuthViewModel extends BaseViewModel {

    @IntDef({
            FIELD.EMAIL,
            FIELD.PASSWORD})
    private @interface FIELD {
        int EMAIL = 0;
        int PASSWORD = 1;
    }

    @IntDef({
            STATE.LOGIN,
            STATE.IDLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        int LOGIN = 0;
        int IDLE = 1;
    }

    private String mEmail = "";
    private String mPassword = "";

    private String mEmailError = null;
    private String mPasswordError = null;

    private int mInputState = STATE.IDLE;

    private void validate(@FIELD int field) {
        Resources resources = mContext.getResources();
        switch (field) {
            case FIELD.EMAIL: {
                mEmailError = isValidEmail()
                        ? null
                        : resources.getString(R.string.auth_email_error);
                notifyPropertyChanged(BR.emailError);
                break;
            }
            case FIELD.PASSWORD: {
                mPasswordError = isValidPassword()
                        ? null
                        : resources.getString(R.string.auth_password_error);
                notifyPropertyChanged(BR.passwordError);
                break;
            }
        }
    }

    private boolean isValidEmail() {
        return !mEmail.isEmpty() &&
                AppConfig.EMAIL_ADDRESS_VALIDATE.matcher(mEmail).matches();
    }

    private boolean isValidPassword() {
        return !mPassword.isEmpty() &&
                AppConfig.PASSWORD_VALIDATE.matcher(mPassword).matches();
    }

    boolean isValid() {
        for (@FIELD int i = 0; i < 2; i++) {
            validate(i);
        }
        return mEmailError == null && mPasswordError == null;
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
        validate(FIELD.EMAIL);
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
        validate(FIELD.PASSWORD);
    }

    @Bindable
    public int getInputState() {
        return mInputState;
    }

    public void setInputState(int inputState) {
        mInputState = inputState;
        notifyPropertyChanged(BR.inputState);
    }

    @Bindable
    public String getEmailError() {
        return mEmailError;
    }

    @Bindable
    public String getPasswordError() {
        return mPasswordError;
    }
}