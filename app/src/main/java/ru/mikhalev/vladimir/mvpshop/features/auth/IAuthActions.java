package ru.mikhalev.vladimir.mvpshop.features.auth;

import android.view.View;

/**
 * @author kenrube
 * @since 24.11.16
 */

public interface IAuthActions {

    void clickOnVk(View view);

    void clickOnFb(View view);

    void clickOnTwitter(View view);

    void clickOnShowCatalog();

    void clickOnLogin();
}
