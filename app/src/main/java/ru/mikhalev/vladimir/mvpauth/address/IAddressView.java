package ru.mikhalev.vladimir.mvpauth.address;

import ru.mikhalev.vladimir.mvpauth.core.layers.view.IView;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */

public interface IAddressView extends IView {
    void showInputError();

    AddressDto getUserAddress();
}
