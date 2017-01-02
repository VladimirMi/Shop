package ru.mikhalev.vladimir.mvpshop.core;

/**
 * Developer Vladimir Mikhalev, 27.10.16
 */

public interface IView {
    void initView();

    void setViewModel(BaseViewModel viewModel);

    boolean viewOnBackPressed();
}
