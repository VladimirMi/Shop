package ru.mikhalev.vladimir.mvpauth.auth;


import android.databinding.Bindable;
import android.os.Handler;

import com.android.databinding.library.baseAdapters.BR;

import javax.inject.Inject;

import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.scopes.AuthScope;
import ru.mikhalev.vladimir.mvpauth.core.layers.presenter.AbstractPresenter;

public class AuthPresenter extends AbstractPresenter<IAuthView> implements IAuthPresenter {
    @Inject
    AuthModel mAuthModel;

    public AuthPresenter() {
        DaggerService.getComponent(Component.class).inject(this);
    }

    //region =============== Binding ==============
    private String mEmail = "";
    private String mPassword = "";

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
    //endregion

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

    private void resetFields() {
        mEmail = "";
        mPassword = "";
    }




    //region ==================== DI ========================

    @dagger.Module
    public static class Module {
        @Provides
        @AuthScope
        AuthModel provideAuthModel() {
            return new AuthModel();
        }
    }

    @dagger.Component(modules = Module.class)
    @AuthScope
    interface Component {
        void inject(AuthPresenter presenter);
    }

    //endregion
}
