package ru.mikhalev.vladimir.mvpauth.core.base;

import android.support.v4.app.Fragment;

import ru.mikhalev.vladimir.mvpauth.RootActivity;
import ru.mikhalev.vladimir.mvpauth.core.base.view.IView;

/**
 * Developer Vladimir Mikhalev, 30.10.2016.
 */

public class BaseFragment extends Fragment implements IView {
    @Override
    public void showMessage(String message) {
        getRootActivity().showMessage(message);
    }

    @Override
    public void showError(Throwable e) {
        getRootActivity().showError(e);
    }

    @Override
    public void showLoad() {
        getRootActivity().showLoad();
    }

    @Override
    public void hideLoad() {
        getRootActivity().hideLoad();
    }

    public RootActivity getRootActivity() {
        return (RootActivity) getActivity();
    }
}
