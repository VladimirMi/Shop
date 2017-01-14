package ru.mikhalev.vladimir.mvpshop.features.address;

import ru.mikhalev.vladimir.mvpshop.core.IView;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

public interface IAddressView extends IView {
    void setViewModel(AddressViewModel viewModel);

    void showInputError();

    AddressViewModel getUserAddress();
}
