package ru.mikhalev.vladimir.mvpauth.catalog;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */

public interface IProductPresenter {
    void clickOnPlus();

    void clickOnMinus();

    void clickOnShowMore();

    void clickOnFavorite(boolean checked);
}
