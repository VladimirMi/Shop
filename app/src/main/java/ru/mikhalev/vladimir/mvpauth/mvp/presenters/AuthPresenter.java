package ru.mikhalev.vladimir.mvpauth.mvp.presenters;


import android.content.Context;
import android.support.annotation.Nullable;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.data.managers.DataManager;
import ru.mikhalev.vladimir.mvpauth.mvp.models.AuthModel;
import ru.mikhalev.vladimir.mvpauth.mvp.views.IAuthView;
import ru.mikhalev.vladimir.mvpauth.utils.ConstantManager;
import ru.mikhalev.vladimir.mvpauth.utils.MyTextWatcher;

public class AuthPresenter implements IAuthPresenter {
    private static AuthPresenter ourInstance = new AuthPresenter();
    private static Context sAppContext = DataManager.getInstance().getAppContext();
    private AuthModel mAuthModel;
    private IAuthView mAuthView;

    private AuthPresenter() {
        mAuthModel = new AuthModel();
    }

    public static AuthPresenter getInstance() {
        return ourInstance;
    }

    @Override
    public void takeView(IAuthView authView) {
        mAuthView = authView;
    }

    @Override
    public void dropView() {
        mAuthView = null;
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

    @Nullable
    @Override
    public IAuthView getView() {
        return mAuthView;
    }

    @Override
    public void clickOnLogin() {
        if (getView()!=null && getView().getAuthPanel() != null) {
            if (getView().getAuthPanel().isIdle()) {
                getView().getAuthPanel().setCustomState(ConstantManager.LOGIN_STATE);
            } else {
                String email = getView().getAuthPanel().getUserEmail();
                String password = getView().getAuthPanel().getUserPassword();

                if (!MyTextWatcher.isValidEmail(email)) {
                    getView().requestEmailFocus();
                    getView().showMessage(sAppContext.getString(R.string.err_msg_email));
                    return;
                }
                if (!MyTextWatcher.isValidPassword(password)) {
                    getView().requestPasswordFocus();
                    getView().showMessage(sAppContext.getString(R.string.err_msg_password));
                    return;
                }
                // TODO: 20-Oct-16 auth user
                mAuthModel.loginUser(email, password);
                getView().showLoad();
                getView().showMessage("request for user auth");
            }
        }
    }

    @Override
    public void clickOnFb() {
        if (getView() != null && getView().getAuthPanel() != null) {
            getView().startFbAnimation();
        }
    }

    @Override
    public void clickOnVk() {
        if (getView() != null && getView().getAuthPanel() != null) {
            getView().startVkAnimation();
        }
    }

    @Override
    public void clickOnTwitter() {
        if (getView() != null && getView().getAuthPanel() != null) {
            getView().startTwAnimation();
        }
    }

    @Override
    public void clickOnShowCatalog() {
        if (getView() != null) {
            getView().showMessage(sAppContext.getString(R.string.show_catalog));
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mAuthModel.isAuthUser();
    }
}
