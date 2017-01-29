package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public interface IProductActions {
    void clickOnPlus();

    void clickOnMinus();

    void clickOnShowMore();

    void clickOnFavorite(boolean isChecked);
}
