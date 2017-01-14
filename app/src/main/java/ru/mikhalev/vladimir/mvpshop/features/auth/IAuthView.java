package ru.mikhalev.vladimir.mvpshop.features.auth;


import ru.mikhalev.vladimir.mvpshop.core.IView;

public interface IAuthView extends IView {

    void setViewModel(AuthViewModel viewModel);
}
