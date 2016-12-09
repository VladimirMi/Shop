package ru.mikhalev.vladimir.mvpauth.core.layers.view;

import ru.mikhalev.vladimir.mvpauth.core.base.BaseViewModel;

/**
 * Developer Vladimir Mikhalev, 27.10.16
 */

public interface IView {

    void setViewModel(BaseViewModel viewModel);

    boolean viewOnBackPressed();
}
