package ru.mikhalev.vladimir.mvpauth.core.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpauth.data.managers.DataManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Component(dependencies = AppComponent.class,
        modules = {NetworkModule.class, LocaleModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
