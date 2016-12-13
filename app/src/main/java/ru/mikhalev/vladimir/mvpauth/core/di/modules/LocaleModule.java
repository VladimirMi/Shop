package ru.mikhalev.vladimir.mvpauth.core.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.mikhalev.vladimir.mvpauth.data.managers.PreferencesManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Module
public class LocaleModule {

    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }
}

