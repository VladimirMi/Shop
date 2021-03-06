package ru.mikhalev.vladimir.mvpshop.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.mikhalev.vladimir.mvpshop.data.managers.PreferencesManager;
import ru.mikhalev.vladimir.mvpshop.data.managers.RealmManager;

/**
 * Developer Vladimir Mikhalev, 06.11.2016.
 */

@Module
public class LocaleModule extends FlavorLocaleModule {

    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }

}

