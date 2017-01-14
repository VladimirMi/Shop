package ru.mikhalev.vladimir.mvpshop.features.details.description;

import ru.mikhalev.vladimir.mvpshop.core.IView;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductViewModel;

/**
 * Developer Vladimir Mikhalev, 14.01.2017.
 */
public interface IDescriptionView extends IView {
    void setViewModel(ProductViewModel viewModel);
}
