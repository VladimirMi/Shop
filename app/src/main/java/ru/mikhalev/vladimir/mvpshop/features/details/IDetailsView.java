package ru.mikhalev.vladimir.mvpshop.features.details;

import ru.mikhalev.vladimir.mvpshop.core.IView;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;

/**
 * Developer Vladimir Mikhalev 23.12.2016
 */

public interface IDetailsView extends IView {

    void setProduct(ProductRealm productRealm);
}
