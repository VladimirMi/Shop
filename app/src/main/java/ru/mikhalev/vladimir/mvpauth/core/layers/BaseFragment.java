package ru.mikhalev.vladimir.mvpauth.core.layers;

import android.support.v4.app.Fragment;

import ru.mikhalev.vladimir.mvpauth.core.layers.view.IView;
import ru.mikhalev.vladimir.mvpauth.home.HomeActivity;

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

    public HomeActivity getRootActivity() {
        return (HomeActivity) getActivity();
    }
}
