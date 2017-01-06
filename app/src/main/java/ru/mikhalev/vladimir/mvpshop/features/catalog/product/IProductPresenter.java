package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */
public interface IProductPresenter {
    // TODO: 06.01.2017  смысл сохранять в бд?
    void clickOnPlus();

    void clickOnMinus();

    void clickOnShowMore();

    void clickOnFavorite(boolean checked);
}
