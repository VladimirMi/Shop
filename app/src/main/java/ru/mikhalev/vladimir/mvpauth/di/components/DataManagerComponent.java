package ru.mikhalev.vladimir.mvpauth.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.mikhalev.vladimir.mvpauth.core.managers.DataManager;
import ru.mikhalev.vladimir.mvpauth.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpauth.di.modules.NetworkModule;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Component(dependencies = AppComponent.class,
        modules = {NetworkModule.class, LocaleModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
