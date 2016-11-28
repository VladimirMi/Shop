package ru.mikhalev.vladimir.mvpauth.core.layers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import ru.mikhalev.vladimir.mvpauth.core.layers.view.IView;
import ru.mikhalev.vladimir.mvpauth.root.IRootView;
import ru.mikhalev.vladimir.mvpauth.root.RootActivity;

/**
 * Developer Vladimir Mikhalev, 30.10.2016.
 */

public class BaseFragment extends Fragment implements IRootView {
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

    @Nullable
    @Override
    public IView getCurrentScreen() {
        return null;
    }

    public RootActivity getRootActivity() {
        return (RootActivity) getActivity();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
