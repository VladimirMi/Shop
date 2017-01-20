package ru.mikhalev.vladimir.mvpshop.features.root;

import android.support.annotation.Nullable;

import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountViewModel;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */
public interface IRootView {

    void showMessage(String message);

    void showError(Throwable e);

    void showLoad();

    void hideLoad();

    @Nullable
    BaseView getCurrentScreen();

    void setBasketCounter(int count);

    void showPermissionSnackBar();

    void openApplicationSettings();

    void setDrawer(AccountViewModel accountViewModel);
}
