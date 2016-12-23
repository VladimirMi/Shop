package ru.mikhalev.vladimir.mvpshop.features.catalog;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */

public interface IProductCardPresenter {
    void clickOnPlus();

    void clickOnMinus();

    void clickOnShowMore();

    void clickOnFavorite(boolean checked);
}
