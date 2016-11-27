package ru.mikhalev.vladimir.mvpauth.auth;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.layers.BaseFragment;
import ru.mikhalev.vladimir.mvpauth.databinding.FragmentAuthBinding;

/**
 * Developer Vladimir Mikhalev, 30.10.2016.
 */

public class AuthFragment extends BaseFragment implements IAuthView, View.OnClickListener {
    public static final String TAG = "AuthFragment";
    AuthPresenter mPresenter = new AuthPresenter();

    private FragmentAuthBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false);
        mPresenter.takeView(this);
        mPresenter.clickOnLogin();
        mBinding.setPresenter(mPresenter);
        mBinding.loginBtn.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }

    //region =============== IAuthView ==============
    @Override
    public void showLoginBtn() {
        // nothing to do here
    }

    @Override
    public void hideLoginBtn() {
        // nothing to do here
    }

    @Override
    public AuthPanel getAuthPanel() {
        return mBinding.authPanel;
    }

    @Override
    public void showEmailError(boolean error) {
        if (error) {
            mBinding.email.setError(this.getString(R.string.auth_email_error));
            requestFocus(mBinding.emailEt);
        } else {
            mBinding.email.setErrorEnabled(false);
        }
    }

    @Override
    public void showPasswordError(boolean error) {
        if (error) {
            mBinding.password.setError(this.getString(R.string.auth_password_error));
            requestFocus(mBinding.passwordEt);
        } else {
            mBinding.password.setErrorEnabled(false);
        }
    }

    @Override
    public void showCatalogScreen() {
        getRootActivity().onBackPressed();
    }

    @Override
    public void showLoad() {
//        mBinding.dotLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
//        mBinding.dotLoader.setVisibility(View.INVISIBLE);
    }

    //endregion

    private void requestFocus(EditText editText) {
        if (editText.requestFocus()) {
            getRootActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            editText.setSelection(editText.length());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn) {
            mPresenter.clickOnLogin();
        }
    }
}
