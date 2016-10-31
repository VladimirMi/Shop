package ru.mikhalev.vladimir.mvpauth.auth;


import android.databinding.Bindable;
import android.os.Handler;

import com.android.databinding.library.baseAdapters.BR;

import ru.mikhalev.vladimir.mvpauth.core.base.presenter.AbstractPresenter;
import ru.mikhalev.vladimir.mvpauth.core.utils.AppConfig;

public class AuthPresenter extends AbstractPresenter<IAuthView> implements IAuthPresenter {
    private static AuthPresenter ourInstance = new AuthPresenter();
    private AuthModel mAuthModel;
    private IAuthView mAuthView;
    private String mEmail = "";
    private String mPassword = "";

    private AuthPresenter() {
        mAuthModel = new AuthModel();
    }

    public static AuthPresenter getInstance() {
        return ourInstance;
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setEmail(String email) {
        mEmail = email.trim();
        notifyPropertyChanged(BR.email);
        validateEmail();
    }

    public void setPassword(String password) {
        mPassword = password.trim();
        notifyPropertyChanged(BR.password);
        validatePassword();
    }

    @Override
    public void initView() {
        if (getView() != null) {
            if (checkUserAuth()) {
                getView().hideLoginBtn();
            } else {
                getView().showLoginBtn();
            }
        }
    }

    @Override
    public void clickOnLogin() {
        if (getView() != null && getView().getAuthPanel() != null) {
            if (getView().getAuthPanel().isIdle()) {
                getView().getAuthPanel().setCustomState(AuthPanel.LOGIN_STATE);
            } else {

                if (!validateEmail()) {
                    return;
                }
                if (!validatePassword()) {
                    return;
                }
                mAuthModel.loginUser(mPassword, mEmail);

                // TODO: 30.10.2016 Registration stab
                getView().showLoad();
                resetFields();
                getView().showMessage("request for user auth");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getView().showCatalogScreen();
                    }
                }, 3000);
            }
        }
    }

    @Override
    public void clickOnFb() {
        if (getView() != null && getView().getAuthPanel() != null) {
            // TODO: 30.10.2016 fb logic
        }
    }

    @Override
    public void clickOnVk() {
        if (getView() != null && getView().getAuthPanel() != null) {
            // TODO: 30.10.2016 vk logic
        }
    }

    @Override
    public void clickOnTwitter() {
        if (getView() != null && getView().getAuthPanel() != null) {
            // TODO: 30.10.2016 tw logic
        }
    }

    @Override
    public void clickOnShowCatalog() {
        if (getView() != null) {
            // TODO: 27-Oct-16 if update data complete start catalog screen
            getView().showCatalogScreen();
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mAuthModel.isAuthUser();
    }

    @Override
    public boolean validateEmail() {
        if (getView() != null) {
            if (isValidEmail(mEmail)) {
                getView().showEmailError(false);
                return true;
            } else {
                getView().showEmailError(true);
            }
        }
        return false;
    }

    @Override
    public boolean validatePassword() {
        if (getView() != null) {

            if (isValidPassword(mPassword)) {
                getView().showPasswordError(false);
                return true;
            } else {
                getView().showPasswordError(true);
            }
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        return !email.isEmpty() &&
                AppConfig.EMAIL_ADDRESS_VALIDATE.matcher(email.trim()).matches();
    }

    private boolean isValidPassword(String password) {
        return !password.isEmpty() &&
                AppConfig.PASSWORD_VALIDATE.matcher(password.trim()).matches();
    }

    private void resetFields() {
        mEmail = "";
        mPassword = "";
    }
}
