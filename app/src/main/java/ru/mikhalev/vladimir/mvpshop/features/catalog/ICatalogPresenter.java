package ru.mikhalev.vladimir.mvpshop.features.catalog;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public interface ICatalogPresenter {
    void clickOnBuyButton(int position);

    boolean checkUserAuth();
}
