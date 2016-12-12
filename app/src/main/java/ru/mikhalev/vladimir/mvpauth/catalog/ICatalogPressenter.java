package ru.mikhalev.vladimir.mvpauth.catalog;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public interface ICatalogPressenter {
    void clickOnBuyButton(int position);

    boolean checkUserAuth();
}
