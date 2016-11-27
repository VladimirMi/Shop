package ru.mikhalev.vladimir.mvpauth.core.di.components;

import android.content.Context;

import dagger.Component;
import ru.mikhalev.vladimir.mvpauth.auth.AuthViewModel;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.AppModule;

/**
 * Developer Vladimir Mikhalev 05.11.2016
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();

    void inject(AuthViewModel authViewModel);
}