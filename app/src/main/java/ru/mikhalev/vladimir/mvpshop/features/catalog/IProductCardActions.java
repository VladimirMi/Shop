package ru.mikhalev.vladimir.mvpshop.features.catalog;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IProductCardActions {
    void clickOnPlus();

    void clickOnMinus();

    void clickOnShowMore();

    void clickOnFavorite(boolean checked);
}
