package ru.mikhalev.vladimir.mvpauth.root;

import ru.mikhalev.vladimir.mvpauth.core.layers.presenter.AbstractPresenter;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */
public class RootPresenter  extends AbstractPresenter<IRootView>{
    public static final String REQUEST_PERMISSION_CAMERA = "REQUEST_PERMISSION_CAMERA";

    @Override
    public void initView() {
        // TODO: 06.11.2016 init avatar + username + state
    }

    public boolean checkPermissionsAndRequestIfNotGranted(String[] permissions, String requestPermissionCamera) {
        return false;
    }
}
