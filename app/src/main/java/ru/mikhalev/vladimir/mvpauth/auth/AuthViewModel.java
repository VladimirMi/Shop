package ru.mikhalev.vladimir.mvpauth.auth;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.BR;
import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.AppComponent;
import ru.mikhalev.vladimir.mvpauth.core.utils.AppConfig;

/**
 * @author kenrube
 * @since 06.11.16
 */

public class AuthViewModel extends BaseObservable {

    @IntDef({
            FIELD.EMAIL,
            FIELD.PASSWORD})
    private @interface FIELD {
        int EMAIL = 0;
        int PASSWORD = 1;
    }

    private String email = "";
    private String password = "";

    private String mEmailError = null;
    private String mPasswordError = null;

    @Inject
    Context mContext;

    AuthViewModel() {
        DaggerService.getComponent(AppComponent.class).inject(this);
    }

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
        return !email.isEmpty() &&
                AppConfig.EMAIL_ADDRESS_VALIDATE.matcher(email).matches();
    }

    private boolean isValidPassword() {
        return !password.isEmpty() &&
                AppConfig.PASSWORD_VALIDATE.matcher(password).matches();
    }

    boolean isValid() {
        for (@FIELD int i = 0; i < 2; i++) {
            validate(i);
        }
        return mEmailError == null && mPasswordError == null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        validate(FIELD.EMAIL);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        validate(FIELD.PASSWORD);
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
