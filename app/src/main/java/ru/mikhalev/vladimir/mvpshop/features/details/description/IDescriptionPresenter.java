package ru.mikhalev.vladimir.mvpshop.features.details.description;

/**
 * Developer Vladimir Mikhalev, 18.01.2017.
 */

public interface IDescriptionPresenter {
    void clickOnPlus();

    void clickOnMinus();

    void clickOnFavorite();

    void clickOnRating(float rating);
}
